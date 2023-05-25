package com.zzx.pluginhook;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * 野线程收敛适配器
 */
class CastThreadAdapter extends ClassVisitor {

    private String className;

    String ignore_thread = "";
    String rep_thread_class = "";
    String rep_thread_return_des = "";
    final String old_thread_extends = "java/util/Thread";

    boolean isInit = false;
    boolean isAbstractMethod = false;

    public CastThreadAdapter() {
        super(Opcodes.ASM6);
    }

    public CastThreadAdapter(ClassVisitor classVisitor, PluginExt pluginExt) {
        super(Opcodes.ASM6, classVisitor);
        System.out.println("thread_des:" + pluginExt.threadpool_des +" method:"+ pluginExt.poolmethod);
        Type typePoolClass = Type.getType(pluginExt.thread_des);
        String threadInternalName = typePoolClass.getInternalName();
        String threadDescripto = typePoolClass.getDescriptor();
        ignore_thread = threadInternalName;
        rep_thread_class = threadInternalName;
        rep_thread_return_des = "()"+threadDescripto;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        if (!isEmpty(old_thread_extends) && old_thread_extends.equals(superName)
                && (!className.equals(ignore_thread)
                && !className.equals(old_thread_extends))) {
            super.visit(version, access, name, signature, ignore_thread, interfaces);
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
            if (className.equals(ignore_thread)
                    || className.equals(old_thread_extends)) {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                return;
            }
            if (opcode == Opcodes.INVOKESPECIAL && old_thread_extends.equals(owner) && name.equals("<init>")) {
                //替换继承类后的构造函数中的替换
                super.visitMethodInsn(opcode, rep_thread_class, name, descriptor, isInterface);
            } else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }


        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW && old_thread_extends.equals(type)
                    && (!className.equals(ignore_thread)
                    && !className.equals(old_thread_extends))) {
                super.visitTypeInsn(opcode, rep_thread_class);
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
