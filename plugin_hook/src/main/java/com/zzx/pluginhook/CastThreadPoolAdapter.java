package com.zzx.pluginhook;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class CastThreadPoolAdapter extends ClassVisitor {

    String exc_descriptor = "Ljava.util.concurrent.ThreadPoolExecutor;";
    String exc_classname;

    private String className;

    final String ignore_thread_pool = "com/zzx/testapp/proxy/JavaThreadPoolExecutorProxy";
    String rep_thread_pool_class = "com/zzx/testapp/proxy/JavaThreadPoolExecutorProxy";
    final String old_thread_pool_field_descriptor = "Ljava.util.concurrent.ThreadPoolExecutor;";
    final String old_thread_pool_extends = "java/util/concurrent/ThreadPoolExecutor";
    String rep_thread_pool_extends_instance = "asw_getInstance";
     String rep_thread_pool_extends_des = "()Lcom/zzx/testapp/proxy/JavaThreadPoolExecutorProxy;";
     String rep_thread_pool_return_des = "()Lcom/zzx/testapp/proxy/JavaThreadPoolExecutorProxy;";
    final String rep_thread_pool_extends2 = "java/util/concurrent/Executors";
    final String rep_thread_pool_extends_des2 = "()Ljava/util/concurrent/ExecutorService;";
    final String rep_thread_pool_extends3 = "java/util/concurrent/ExecutorService";
    final String ignore_thread_runnable = "com/zzx/testapp/proxy/ThreadProxy";

    boolean isInit = false;
    boolean isAbstractMethod = false;

    public CastThreadPoolAdapter() {
        super(Opcodes.ASM6);
    }

    public CastThreadPoolAdapter(ClassVisitor classVisitor, PluginExt pluginExt) {
        super(Opcodes.ASM6, classVisitor);
        System.out.println("thread_des:" + pluginExt.thread_des + " threadclass:"+ pluginExt.threadclass +" method:"+ pluginExt.method);
//        rep_thread_pool_extends_instance = pluginExt.method;
//        rep_thread_pool_extends_des = pluginExt.thread_des;
//        rep_thread_pool_return_des = "()"+rep_thread_pool_extends_des;
//        rep_thread_pool_class = pluginExt.threadclass;
        Type type = Type.getType(exc_descriptor);
        exc_classname = type.getClassName();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        if (!isEmpty(old_thread_pool_extends) && old_thread_pool_extends.equals(superName)
                && (!className.equals(ignore_thread_pool)
                && !className.equals(old_thread_pool_extends)
                && !className.equals(rep_thread_pool_extends2)
                && !className.equals(rep_thread_pool_extends3))) {
            super.visit(version, access, name, signature, ignore_thread_pool, interfaces);
        } else {
            super.visit(version, access, name, signature, superName, interfaces);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (mv != null && "<init>".equals(name)) {
            isAbstractMethod = (access & Opcodes.ACC_ABSTRACT) != 0;
        }
        return new CastMethodAdapter(mv);
    }

    class CastMethodAdapter extends MethodVisitor {

        public CastMethodAdapter() {
            super(Opcodes.ASM6);
        }

        public CastMethodAdapter(MethodVisitor methodVisitor) {
            super(Opcodes.ASM6, methodVisitor);
        }

        @Override
        public void visitCode() {
            super.visitCode();
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (className.equals(ignore_thread_pool)
                    || className.equals(old_thread_pool_extends)
                    || className.equals(rep_thread_pool_extends2)
                    || className.equals(rep_thread_pool_extends3)) {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                return;
            }
            if (opcode == Opcodes.INVOKESPECIAL && old_thread_pool_extends.equals(owner) && name.equals("<init>")) {
                //替换继承类后的构造函数中的替换
                super.visitMethodInsn(opcode, rep_thread_pool_class, name, descriptor, isInterface);
            } else if (opcode == Opcodes.INVOKESTATIC && rep_thread_pool_extends2.equals(owner) &&
                    rep_thread_pool_extends_des2.equals(descriptor) && !isInterface) {
                //替换静态方法调用
                super.visitMethodInsn(opcode, rep_thread_pool_class, rep_thread_pool_extends_instance, rep_thread_pool_return_des, false);
            } else if (opcode == Opcodes.INVOKEVIRTUAL && rep_thread_pool_extends3.equals(owner) && isInterface) {
                super.visitMethodInsn(opcode, rep_thread_pool_class, name, descriptor, false);
            } else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {

            System.out.println("MethodVisitor.visitFieldInsn() = opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", descriptor:" + descriptor);
            if (Opcodes.GETSTATIC == opcode && old_thread_pool_field_descriptor.equals(descriptor)
                    && (!className.equals(ignore_thread_pool)
                    && !className.equals(old_thread_pool_extends)
                    && !className.equals(rep_thread_pool_extends2)
                    && !className.equals(rep_thread_pool_extends3))
            ) {
                super.visitMethodInsn(Opcodes.INVOKESTATIC,rep_thread_pool_class,rep_thread_pool_extends_instance,rep_thread_pool_return_des,false);
            } else {
                super.visitFieldInsn(opcode, owner, name, descriptor);
            }
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW && old_thread_pool_extends.equals(type)
                    && (!className.equals(ignore_thread_pool)
                    && !className.equals(old_thread_pool_extends)
                    && !className.equals(rep_thread_pool_extends2)
                    && !className.equals(rep_thread_pool_extends3))) {
                super.visitTypeInsn(opcode, rep_thread_pool_class);
            }else {
                super.visitTypeInsn(opcode, type);
            }
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
        }
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
