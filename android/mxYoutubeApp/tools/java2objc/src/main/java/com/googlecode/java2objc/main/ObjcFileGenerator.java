package com.googlecode.java2objc.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.code.java2objc.code.ObjcType;
import com.googlecode.java2objc.objc.SourceCodeWriter;

final class ObjcFileGenerator {
  private final File outputDir;
  private final String indent;

  ObjcFileGenerator(File outputDir, String indent) {
    this.outputDir = outputDir;
    this.indent = indent;
  }

  void writeSourceCodeForFiles(List<List<ObjcType>> files) throws IOException {
    for (List<ObjcType> file : files) {
      writeSourceCodeForTypes(file);
    }
  }

  void writeSourceCodeForTypes(List<ObjcType> types) throws IOException {
    SourceCodeWriter headerWriter = null;
    SourceCodeWriter implWriter = null;
    try {
      outputDir.mkdirs();
      File headerFile = new File(outputDir, types.get(0).getHeaderFileName());
      headerWriter =
        new SourceCodeWriter(new PrintWriter(new FileOutputStream(headerFile)), true,
            indent);
      types.get(0).appendHeaderImports(headerWriter);
      File implFile = null;

      for (ObjcType currentType : types) {
        headerWriter.append(currentType);

        if (currentType.hasImpl()) {
          if (implFile == null) {
            implFile = new File(outputDir, types.get(0).getImplFileName());
            implWriter =
              new SourceCodeWriter(new PrintWriter(new FileOutputStream(implFile)), false,
                  indent);
            types.get(0).appendBodyImports(implWriter);
          }
          implWriter.append(currentType);
        }
      }

      System.out.printf("Generated %s\n", headerFile.getAbsolutePath());
      if (implFile != null) {
        System.out.printf("Generated %s\n", implFile.getAbsolutePath());
      }
    } finally {
      if (headerWriter != null) headerWriter.close();
      if (implWriter != null) implWriter.close();
    }
  }
}
