package japa.parser.ast.visitor;

import java.util.Iterator;

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
import japa.parser.ast.expr.Expression;
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
import japa.parser.ast.stmt.Statement;
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
import symtab.ClassSymbol;
import symtab.CompilationUnitSymbol;
import symtab.ConstructorSymbol;
import symtab.MethodSymbol;
import symtab.Scope;
import symtab.VariableSymbol;

public class CreateScopesVisitor implements VoidVisitor<Object>{
	
	private Scope currentScope;

	@Override
	public void visit(Node n, Object arg) {
		// TODO Not sure what this does
		throw new IllegalStateException(n.getClass().getName());
	}

	@Override
	public void visit(CompilationUnit n, Object arg) {
		// TODO Auto-generated method stub

		currentScope = new CompilationUnitSymbol();
		
		System.out.println("Compilation created as new currentScope");
		
		n.setData(currentScope);
		
		// TODO
//		if (n.getPakage() != null) {
//			System.out.println("Going into package");
//            n.getPakage().accept(this, arg);
//        }
		
		// TODO
//        if (n.getImports() != null) {
//        	System.out.println("Imports");
//            for (ImportDeclaration i : n.getImports()) {
//                i.accept(this, arg);
//            }
//        }
        
        if (n.getTypes() != null) {
            for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i.hasNext();) {
            	TypeDeclaration typeDecl = i.next();
            	System.out.println("Going into " + typeDecl.getName());
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
		// TODO interface?
		ClassSymbol classSym = new ClassSymbol(n.getName(), currentScope);
		currentScope.define(classSym);
		
		currentScope = classSym;
		
		n.setData(currentScope);
		
		for(BodyDeclaration member : n.getMembers()){
			if(member instanceof TypeDeclaration){
				System.out.println("Going into " + ((TypeDeclaration) member).getName());
			} else {
				System.out.println("Going into " + member.getClass());
			}
			
			member.accept(this, arg);
		}
		
		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void visit(EnumDeclaration n, Object arg) {
		// TODO this contains children, need to implement
		
	}

	@Override
	public void visit(EmptyTypeDeclaration n, Object arg) {
		// TODO No idea what this is
	}

	@Override
	public void visit(EnumConstantDeclaration n, Object arg) {
		// TODO part of EnumDeclaration scope
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
		for(VariableDeclarator variableDeclarator : n.getVariables()){
			variableDeclarator.accept(this, arg);
		}
		
	}

	@Override
	public void visit(VariableDeclarator n, Object arg) {
		// TODO Auto-generated method stub
		// This is the variable with its initialiser
		// E.g. a = 3 + 4
		System.out.println(n.getId());
		n.setData(currentScope);
	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
		// TODO Auto-generated method stub
		// This is the name of the variable
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		ConstructorSymbol constructorSym = new ConstructorSymbol(n.getName(), currentScope);
		currentScope.define(constructorSym);
		
		currentScope = constructorSym;
		
		n.setData(currentScope);
		
		n.getBlock().accept(this, arg);
		
		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		MethodSymbol methodSym = new MethodSymbol(n.getName(), currentScope);
		currentScope.define(methodSym);
		
		currentScope = methodSym;
		
		n.setData(currentScope);
		
		n.getBody().accept(this, arg);
		
		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void visit(Parameter n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("Empty member");
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
		System.out.println("assign expression: " + n.getTarget());
		n.setData(currentScope);
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
		System.out.println("Variable Declaration expression");
		
		for(VariableDeclarator variableDeclarator : n.getVars()){
			variableDeclarator.accept(this, arg);
		}
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
		// TODO I don't know what this is
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
		// TODO Auto-generated method stub
		n.getTypeDeclaration().accept(this, arg);
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
		// TODO Auto-generated method stub
        n.getCheck().accept(this, arg);
        if (n.getMessage() != null) {
            n.getMessage().accept(this, arg);
        }
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("BlockStmt");
		for(Statement statement : n.getStmts()){
			statement.accept(this, arg);
		}
	}

	@Override
	public void visit(LabeledStmt n, Object arg) {
		// TODO Auto-generated method stub
		n.getStmt().accept(this, arg);
	}

	@Override
	public void visit(EmptyStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExpressionStmt n, Object arg) {
		// TODO Auto-generated method stub
		n.getExpression().accept(this, arg);
	}

	@Override
	public void visit(SwitchStmt n, Object arg) {
		// TODO Auto-generated method stub
        n.getSelector().accept(this, arg);
        if (n.getEntries() != null) {
            for (SwitchEntryStmt e : n.getEntries()) {
                e.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(SwitchEntryStmt n, Object arg) {
		// TODO Auto-generated method stub
		if (n.getLabel() != null) {
            n.getLabel().accept(this, arg);
        }
        if (n.getStmts() != null) {
            for (Statement s : n.getStmts()) {
                s.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(BreakStmt n, Object arg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ReturnStmt n, Object arg) {
		// TODO Auto-generated method stub
		if (n.getExpr() != null) {
            n.getExpr().accept(this, arg);
        }
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		// TODO Auto-generated method stub
		n.getCondition().accept(this, arg);
        n.getThenStmt().accept(this, arg);
        if (n.getElseStmt() != null) {
            n.getElseStmt().accept(this, arg);
        }
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		// TODO Auto-generated method stub
        n.getCondition().accept(this, arg);
        n.getBody().accept(this, arg);
		
	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoStmt n, Object arg) {
		// TODO Auto-generated method stub
        n.getBody().accept(this, arg);
        n.getCondition().accept(this, arg);
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		// TODO Auto-generated method stub
        n.getVariable().accept(this, arg);
        n.getIterable().accept(this, arg);
        n.getBody().accept(this, arg);
	}

	@Override
	public void visit(ForStmt n, Object arg) {
		// TODO Auto-generated method stub
        if (n.getInit() != null) {
            for (Iterator<Expression> i = n.getInit().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }

        if (n.getCompare() != null) {
            n.getCompare().accept(this, arg);
        }

        if (n.getUpdate() != null) {
            for (Iterator<Expression> i = n.getUpdate().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
        n.getBody().accept(this, arg);
	}

	public void visit(ThrowStmt n, Object arg) {
        n.getExpr().accept(this, arg);
    }

    public void visit(SynchronizedStmt n, Object arg) {
        n.getExpr().accept(this, arg);
        n.getBlock().accept(this, arg);
    }

    public void visit(TryStmt n, Object arg) {
        n.getTryBlock().accept(this, arg);
        if (n.getCatchs() != null) {
            for (CatchClause c : n.getCatchs()) {
                c.accept(this, arg);
            }
        }
        if (n.getFinallyBlock() != null) {
            n.getFinallyBlock().accept(this, arg);
        }
    }

    public void visit(CatchClause n, Object arg) {
        n.getExcept().accept(this, arg);
        n.getCatchBlock().accept(this, arg);
    }
	
}