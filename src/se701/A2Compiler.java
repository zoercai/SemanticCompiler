package se701;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.visitor.SillyBreakVisitor;
import japa.parser.ast.visitor.CheckInheritanceVisitor;
import japa.parser.ast.visitor.CheckTerminalVisitor;
import japa.parser.ast.visitor.CreateScopesVisitor;
import japa.parser.ast.visitor.DefineTerminalVisitor;
import japa.parser.ast.visitor.DumpVisitor;
import japa.parser.ast.visitor.SemanticsVisitor;

public class A2Compiler {
	
	/*
	 * This is the only method you should need to change inside this class. But do not modify the signature of the method! 
	 */
	public static void compile(File file) throws ParseException, FileNotFoundException {

		// parse the input, performs lexical and syntatic analysis
		JavaParser parser = new JavaParser(new FileReader(file));
		CompilationUnit ast = parser.CompilationUnit();
		
//		SillyBreakVisitor sillyVisitor = new SillyBreakVisitor();
//		ast.accept(sillyVisitor, null);
		
		System.out.println("Adding scope");
		CreateScopesVisitor scopesVisitor = new CreateScopesVisitor();
		ast.accept(scopesVisitor, null);
		
		System.out.println("Checking inheritance");
		CheckInheritanceVisitor inheritanceVisitor = new CheckInheritanceVisitor();
		ast.accept(inheritanceVisitor, null);
		
		System.out.println("Adding terminals");
		DefineTerminalVisitor defineTerminalVisitor = new DefineTerminalVisitor();
		ast.accept(defineTerminalVisitor, null);
		
		System.out.println("Checking types");
		CheckTerminalVisitor checkTerminalVisitor = new CheckTerminalVisitor();
		ast.accept(checkTerminalVisitor, null);
		
		// perform visit 2... etc etc 
		// ...
		
		// perform visit N 
		DumpVisitor printVisitor = new DumpVisitor();
		ast.accept(printVisitor, null);
		
		String result = printVisitor.getSource();
		
		// save the result into a *.java file, same level as the original file
		File javaFile = getAsJavaFile(file);
		writeToFile(javaFile, result);
	}
	
	/*
	 * Given a *.javax File, this method returns a *.java File at the same directory location  
	 */
	private static File getAsJavaFile(File javaxFile) {
		String javaxFileName = javaxFile.getName();
		File containingDirectory = javaxFile.getAbsoluteFile().getParentFile();
		String path = containingDirectory.getAbsolutePath()+System.getProperty("file.separator");
		String javaFilePath = path + javaxFileName.substring(0,javaxFileName.lastIndexOf("."))+".java";
		return new File(javaFilePath);
	}
	
	/*
	 * Given the specified file, writes the contents into it.
	 */
	private static void writeToFile(File file, String contents) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);
		writer.print(contents);
		writer.close();
	}
}
