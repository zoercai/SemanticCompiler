package japa.parser.ast.visitor;

import java.util.Iterator;
import java.util.List;

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.SuperMemberAccessExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import se701.A2SemanticsException;
import symtab.ClassOrInterfaceSymbol;
import symtab.Symbol;

public class CheckInheritanceVisitor implements VoidVisitor<Object> {

	@Override
	public void visit(Node n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CompilationUnit n, Object arg) {
		// TODO Auto-generated method stub
		if (n.getTypes() != null) {
            for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i.hasNext();) {
            	TypeDeclaration typeDecl = i.next();
            	typeDecl.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(PackageDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TypeParameter n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LineComment n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BlockComment n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		System.out.println(n.getName());
		
		if(n.getExtends() != null){
			checkClassOrInterfaceInheritance(n, false);
		}
		if(n.getImplements() != null){
			checkClassOrInterfaceInheritance(n, true);
		}
		
		for(BodyDeclaration member : n.getMembers()){
			member.accept(this, arg);
		}
	}

	/**
	 * TODO
	 * @param n
	 */
	private void checkClassOrInterfaceInheritance(ClassOrInterfaceDeclaration n, boolean checkInterface) {
		// TODO potentially refactor to change n argument to more specific
		ClassOrInterfaceSymbol currentClassOrInterfaceSym = (ClassOrInterfaceSymbol) n.getData();
		
		List<ClassOrInterfaceType> parents;
		parents = checkInterface ? n.getImplements() : n.getExtends();
		
		for(ClassOrInterfaceType parentClassType : parents){
			Symbol parentSym = currentClassOrInterfaceSym.resolve(parentClassType.getName());
			
			if(parentSym == null){
				throw new A2SemanticsException(parentClassType.getName() + " on line " + parentClassType.getBeginLine() + " is not defined");
			}
			if(!(parentSym instanceof ClassOrInterfaceSymbol)){
				throw new A2SemanticsException(parentClassType.getName() + " on line " + parentClassType.getBeginLine() + " is not a valid class");
			}
			if(((ClassOrInterfaceSymbol) parentSym).isInterface() != checkInterface){
				throw new A2SemanticsException(parentClassType.getName() + " on line " + parentClassType.getBeginLine() + " is not the correct class type");
			}
			
			if(checkInterface){
				currentClassOrInterfaceSym.addImplement((ClassOrInterfaceSymbol) parentSym);
			} else {
				currentClassOrInterfaceSym.addExtend((ClassOrInterfaceSymbol) parentSym);
			}
		}
	}

	@Override
	public void visit(EnumDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EmptyTypeDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EnumConstantDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnnotationDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnnotationMemberDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FieldDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VariableDeclarator n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Parameter n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InitializerDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JavadocComment n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassOrInterfaceType n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PrimitiveType n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ReferenceType n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VoidType n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WildcardType n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayAccessExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayCreationExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayInitializerExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssignExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BinaryExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CastExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ConditionalExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EnclosedExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FieldAccessExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InstanceOfExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StringLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntegerLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LongLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LongLiteralMinValueExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CharLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BooleanLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NullLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MethodCallExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NameExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ObjectCreationExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(QualifiedNameExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SuperMemberAccessExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ThisExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SuperExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UnaryExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VariableDeclarationExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MarkerAnnotationExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NormalAnnotationExpr n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MemberValuePair n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LabeledStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EmptyStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExpressionStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SwitchStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SwitchEntryStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BreakStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ReturnStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ForStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ThrowStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SynchronizedStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TryStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CatchClause n, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
