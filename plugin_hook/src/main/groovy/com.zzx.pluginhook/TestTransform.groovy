package com.zzx.pluginhook;

import com.android.build.api.transform.*;
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor
import org.gradle.api.Project;


class TestTransform extends Transform {
   final static String NAME = "XerathTransform"
   private WaitableExecutor waitableExecutor

   private Project project
   TestTransform(Project project) {

      this.project = project
      this.waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool()
      println("开始编写gradle插件")
   }

   @Override
   String getName() {
      return NAME
   }

   @Override
   Set<QualifiedContent.ContentType> getInputTypes() {
      return TransformManager.CONTENT_CLASS
   }

   @Override
   Set<? super QualifiedContent.Scope> getScopes() {
      return TransformManager.SCOPE_FULL_PROJECT
   }

   @Override
   boolean isIncremental() {
      return false
   }

   @Override
   void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
      super.transform(transformInvocation)
      println("开始运行插件了")
   }
}
