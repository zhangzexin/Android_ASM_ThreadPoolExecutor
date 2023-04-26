package com.zzx.pluginhook;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.ClassNode;

class CastBytecodes extends ClassVisitor implements Opcodes {

    boolean isShowLog = false;
    String isShowLogClass = "TestActivity";
    private String name;
    private String classExclude = "com/zzx/testapp/otherpool/SimplePool";
    private String classReplacement = "com/zzx/testapp/otherpool/SimplePool";

    public CastBytecodes() {
        super(Opcodes.ASM6);
    }

    public CastBytecodes(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.name = name;
        super.visit(version, access, name, signature, superName, interfaces);
        isShowLog = name.contains(isShowLogClass);
        showLog("visit() = version:" + version + ", access:" + access + ", name:" + name + ", superName:" + superName + ", interfaces:" + interfaces);
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
        showLog("visitSource() = source:" + source + ", debug:" + debug);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        showLog("visitField() = access:" + access + ", name:" + name + ", descriptor:" + descriptor + ", signature:" + signature + ", value:" + value);
        FieldVisitor fv;
        fv = super.visitField(access, name, descriptor, signature, value);
        return new CastFieldVisitor(fv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        showLog("visitMethod() = access:" + access + ", name:" + name + ", descriptor:" + descriptor + ", signature:" + signature + ", exceptions:" + exceptions);
//        return super.visitMethod(access, name, descriptor, signature, exceptions);
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new CastMethodVisitor(mv);
    }

    @Override
    public void visitNestHost(String nestHost) {
        showLog("visitNestHost() = nestHost:" + nestHost);
        super.visitNestHost(nestHost);
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        showLog("visitOuterClass() = owner:" + owner + ", name:" + name + ", descriptor:" + descriptor);
        super.visitOuterClass(owner, name, descriptor);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        showLog("visitAttribute() = attribute:" + attribute.type);
        super.visitAttribute(attribute);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        showLog("visitInnerClass() = name:" + name + ", outerName:" + outerName + ", innerName:" + innerName);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        showLog("visitModule() = name:" + name + ", access:" + access + ", version:" + version);
        return super.visitModule(name, access, version);
    }

    @Override
    public void visitEnd() {
        showLog("visitEnd()");
        super.visitEnd();
    }

    class CastMethodVisitor extends MethodVisitor implements Opcodes {

        public CastMethodVisitor() {
            super(Opcodes.ASM6);
        }

        public CastMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM6, methodVisitor);
        }

        @Override
        public void visitCode() {
            super.visitCode();
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(maxStack, maxLocals);
        }

        @Override
        public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
            showLog("MethodVisitor.visitFrame() = type:" + type + ", numLocal:" + numLocal + ", local:" + local + ", numStack:" + numStack + ", stack:" + stack);
            super.visitFrame(type, numLocal, local, numStack, stack);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            showLog("MethodVisitor.visitFieldInsn() = opcode:" + opcode + ", owner:" + owner + ", name:" + name+ ", descriptor:" + descriptor);
//            if (Opcodes.GETSTATIC == opcode && "Lcom/zzx/testapp/base/BaseThreadPoolExecutor;".equals(descriptor)
//                    && !classExclude.equals(CastBytecodes.this.name)) {
//                super.visitFieldInsn(opcode, classReplacement, "poolExecutor", descriptor);
//            } else {
                super.visitFieldInsn(opcode, owner, name, descriptor);
//            }
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            showLog("MethodVisitor.visitTypeInsn() = opcode:" + opcode + ", type:" + type);
//            if (Opcodes.NEW == opcode && name != null && !name.equals("com/example/material_test/TestHandler.java") && !isEmpty(type) && type.equals("com/example/material_test/ReleaseHandler")) {
//                super.visitTypeInsn(opcode, "com/example/material_test/TestHandler");
//            } else {
                super.visitTypeInsn(opcode, type);
//            }
        }

        @Override
        public void visitLdcInsn(Object value) {
            showLog("MethodVisitor.visitLdcInsn() = value:" + value.toString());
            if (value instanceof String && value.equals("hahaha")) {
                super.visitLdcInsn("\u4fee\u6539\u6210\u529f");
            } else {
                super.visitLdcInsn(value);
            }
        }

        @Override
        public void visitEnd() {
            showLog("MethodVisitor.visitEnd() End");
            super.visitEnd();
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            showLog("MethodVisitor.visitMethodInsn() opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", descriptor:" + descriptor + ", isInterface:" + isInterface);
//            if (name != null && !name.equals("com/example/material_test/TestHandler.java") && !isEmpty(owner) && owner.equals("com/example/material_test/ReleaseHandler")) {
//                super.visitMethodInsn(opcode, "com/example/material_test/TestHandler", name, descriptor, isInterface);
//            } else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
//            }
        }

        @Override
        public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
            showLog("MethodVisitor.visitLocalVariable() name:" + name + ", descriptor:" + descriptor + ", signature:" + signature + ", start:" + start + ", end:" + end + ", index:" + index);
//            if (name != null && !name.equals("com/example/material_test/TestHandler.java") && !isEmpty(descriptor) && "Lcom/example/material_test/ReleaseHandler;".equals(descriptor)) {
//                super.visitLocalVariable(name, "Lcom/example/material_test/TestHandler;", signature, start, end, index);
//            } else {
                super.visitLocalVariable(name, descriptor, signature, start, end, index);
//            }
        }


    }

    class CastFieldVisitor extends FieldVisitor {

        public CastFieldVisitor() {
            super(Opcodes.ASM6);
        }

        public CastFieldVisitor(FieldVisitor fieldVisitor) {
            super(Opcodes.ASM6, fieldVisitor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            showLog("CastFieldVisitor.visitAnnotation() descriptor:" + name + ", visible:" + visible);
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
            showLog("CastFieldVisitor.visitTypeAnnotation() typeRef:" + typeRef + ", typePath:" + typePath.toString() + ", descriptor:" + descriptor + ", visible:" + visible);
            return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
        }

        @Override
        public void visitAttribute(Attribute attribute) {
            showLog("CastFieldVisitor.visitAttribute() attribute:" + attribute.type);
            super.visitAttribute(attribute);
        }

        @Override
        public void visitEnd() {
            showLog("CastFieldVisitor.visitEnd()");
            super.visitEnd();
        }
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public void showLog(String string) {
        if (isShowLog) {
            System.out.println(string);
        }
    }
}
