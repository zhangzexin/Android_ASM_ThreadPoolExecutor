package com.zzx.pluginhook

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.stream.Collectors
import java.util.stream.Stream
import java.util.zip.ZipEntry

class ClassTransform extends Transform {
    PluginExt mPluginExt

    public ClassTransform(PluginExt pluginExt){
        this.mPluginExt = pluginExt
    }
    @Override
    public String getName() {
        return "MyCustomTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        System.out.println("开始Transform");
        if (!transformInvocation.isIncremental()) {
            transformInvocation.getOutputProvider().deleteAll();
        }
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        if (!inputs.isEmpty()) {
            for (TransformInput input : inputs) {
                Collection<DirectoryInput> directoryInputs = input.getDirectoryInputs();
                handleDirInputs(transformInvocation, directoryInputs);
                Collection<JarInput> jarInputs = input.getJarInputs();
                handleJarInputs(transformInvocation, jarInputs);
            }
        }
    }

    private void handleJarInputs(TransformInvocation transformInvocation, Collection<JarInput> jarInputs) {
        for (final def jarInput in jarInputs) {
            if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
                //重名名输出文件,因为可能同名,会覆盖
                def jarName = jarInput.name
//                def md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                JarFile jarFile = new JarFile(jarInput.file)
                Enumeration enumeration = jarFile.entries()
                File tempFile = new File(jarInput.file.parent + File.separator + "temp.jar")
                //避免上次的缓存被重复插入
                if (tempFile.exists()) {
                    tempFile.delete()
                }
                JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tempFile))
                //保存
                while (enumeration.hasMoreElements()) {
                    JarEntry jarEntry = enumeration.nextElement()
                    String entryName = jarEntry.name
                    ZipEntry zipEntry = new ZipEntry(entryName)
                    InputStream inputStream = jarFile.getInputStream(zipEntry)
                    if (isClassFile(entryName)) {
                        jarOutputStream.putNextEntry(zipEntry)
                        ClassReader classReader = new ClassReader(inputStream)
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor classVisitor = new CastThreadPoolAdapter(classWriter,mPluginExt)
                        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                        byte[] bytes = classWriter.toByteArray()
                        jarOutputStream.write(bytes)
                    } else {
                        jarOutputStream.putNextEntry(zipEntry)
                        jarOutputStream.write(inputStream.bytes)
                    }
                    jarOutputStream.closeEntry()
                }

                jarOutputStream.close()
                jarFile.close()
                def dest = transformInvocation.getOutputProvider().getContentLocation(jarName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(tempFile, dest)
                tempFile.delete()
            }

        }
    }

    private void handleDirInputs(TransformInvocation transformInvocation, Collection<DirectoryInput> directoryInputs) throws IOException {
        for (DirectoryInput directoryInput : directoryInputs) {
            if (directoryInput.getFile().isDirectory()) {
                Stream<Path> walk = Files.walk(Paths.get(directoryInput.getFile().getPath()));
                List<File> collect = walk.filter(Files::isRegularFile)
                        .filter(path -> {
                            String name = path.getFileName().toString();
                            return name.endsWith(".class");
                        })
                        .map(path -> path.toFile())
                        .collect(Collectors.toList());
                for (File file : collect) {
                    System.out.println("class:" + file.getName());
                    System.out.println("path:" + file.getParentFile().getAbsolutePath() + File.separator + file.getName());
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.close();
                    ClassReader classReader = new ClassReader(Files.readAllBytes(file.toPath()));
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                    ClassVisitor classVisitor = new CastThreadPoolAdapter(classWriter,mPluginExt);
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
//                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//                    TestClassNode node = new TestClassNode(classWriter)
//                    classReader.accept(node, ClassReader.EXPAND_FRAMES)
                    byte[] bytes = classWriter.toByteArray();
                    FileOutputStream fileOutputStream = new FileOutputStream(file.getParentFile().getAbsolutePath() + File.separator + file.getName());
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                }
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName()
                        , directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
                System.out.println("Output:" + dest.getPath());
                System.out.println("Input:" + directoryInput.getFile().getPath());
                FileUtils.copyDirectory(directoryInput.getFile(), dest);
            }
        }

    }

    /**
     * 判断是否为需要处理class文件
     * @param name
     * @return
     */
    boolean isClassFile(String name) {
        return (name.endsWith(".class") && !name.startsWith("R\$")
                && "R.class" != name && "BuildConfig.class" != name)
    }

}
