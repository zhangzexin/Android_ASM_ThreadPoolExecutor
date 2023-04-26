package com.zzx.pluginhook;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

class TestClassNode extends ClassNode {

   public TestClassNode(ClassVisitor cv) {
      super(Opcodes.ASM6);
      this.cv = cv;
   }

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      super.visit(version, access, name, signature, superName, interfaces);
   }

   @Override
   public void visitEnd() {
      super.visitEnd();
      if (cv != null) {
         accept(cv);
      }
   }
}
