package com.zzx.pluginhook;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

class TempAdapter extends ClassVisitor {
   public TempAdapter() {
      super(Opcodes.ASM6);
   }

   public TempAdapter(ClassVisitor classVisitor) {
      super(Opcodes.ASM6, classVisitor);
   }
}
