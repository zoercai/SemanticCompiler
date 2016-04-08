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
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import se701.A2SemanticsException;
import symtab.ClassOrInterfaceSymbol;
import symtab.CompilationUnitSymbol;
import symtab.ConstructorSymbol;
import symtab.GlobalScope;
import symtab.MethodSymbol;
import symtab.Scope;
import symtab.ScopedSymbol;
import symtab.Symbol;
import symtab.VariableSymbol;

public class DefineTerminalVisitor implements VoidVisitor<Object> {

	@Override
	public void visit(Node n, Object arg) {
		throw new IllegalStateException(n.getClass().getName());
	}

	@Override
	public void visit(CompilationUnit n, Object arg) {
		if (n.getTypes() != null) {
			for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i.hasNext();) {
				TypeDeclaration typeDecl = i.next();
				typeDecl.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(PackageDeclaration n, Object arg) {
		n.getName().accept(this, arg);
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
		n.getName().accept(this, arg);
	}

	@Override
	public void visit(TypeParameter n, Object arg) {
		if (n.getTypeBound() != null) {
			for (Iterator<ClassOrInterfaceType> i = n.getTypeBound().iterator(); i.hasNext();) {
				ClassOrInterfaceType c = i.next();
				c.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(LineComment n, Object arg) {

	}

	@Override
	public void visit(BlockComment n, Object arg) {
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		// TODO test interface
		for (BodyDeclaration member : n.getMembers()) {
			member.accept(this, arg);
		}
	}

	@Override
	public void visit(EnumDeclaration n, Object arg) {
		// TODO this contains children, need to implement
		if (n.getImplements() != null) {
			for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
				ClassOrInterfaceType c = i.next();
				c.accept(this, arg);
			}
		}

		if (n.getEntries() != null) {
			for (Iterator<EnumConstantDeclaration> i = n.getEntries().iterator(); i.hasNext();) {
				EnumConstantDeclaration e = i.next();
				e.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(EmptyTypeDeclaration n, Object arg) {
		n.getJavaDoc().accept(this, arg);
	}

	@Override
	public void visit(EnumConstantDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}

		if (n.getArgs() != null) {
			for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(AnnotationDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
	}

	@Override
	public void visit(AnnotationMemberDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		n.getType().accept(this, arg);
		if (n.getDefaultValue() != null) {
			n.getDefaultValue().accept(this, arg);
		}
	}

	@Override
	public void visit(FieldDeclaration n, Object arg) {
		Type type = n.getType();
		Scope currentScope = (Scope) n.getData();
		Symbol fieldTypeSym = currentScope.resolve(type.toString());

		// Checks the type of the field
		checkType(type, fieldTypeSym);

		for (Iterator<VariableDeclarator> i = n.getVariables().iterator(); i.hasNext();) {
			VariableDeclarator v = i.next();
			System.out.println(v.getId());

			// Checks expression type is the same as field type
			if (v.getInit() != null) {
				symtab.Type typeOfExpression = getTypeOfExpression(v.getInit(), n.getData());
				if (typeOfExpression == null) {
					throw new A2SemanticsException("Expression type is not valid on line " + v.getBeginLine());
				}
				if ((symtab.Type) fieldTypeSym != typeOfExpression) {
					throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to " + type
							+ " on line " + type.getBeginLine());
				}
			}

			// Checks the name of the field
			checkName(currentScope, v.getId());
			VariableSymbol variableSym = new VariableSymbol(v.getId().getName(), (symtab.Type) fieldTypeSym);
			((Scope) n.getData()).define(variableSym);
		}
	}

	@Override
	public void visit(VariableDeclarator n, Object arg) {
		n.getId().accept(this, arg);
		if (n.getInit() != null) {
			n.getInit().accept(this, arg);
		}
	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getParameters() != null) {
			for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
				Parameter p = i.next();
				p.accept(this, arg);
			}
		}

		if (n.getThrows() != null) {
			for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
				NameExpr name = i.next();
				name.accept(this, arg);
			}
		}
		n.getBlock().accept(this, arg);
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		n.getType().accept(this, arg);
		if (n.getParameters() != null) {
			for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
				Parameter p = i.next();
				p.accept(this, arg);
			}
		}
		if (n.getThrows() != null) {
			for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
				NameExpr name = i.next();
				name.accept(this, arg);
			}
		}
		if (n.getBody() != null) {
			n.getBody().accept(this, arg);
		}
	}

	@Override
	public void visit(Parameter n, Object arg) {
		n.getType().accept(this, arg);
		n.getId().accept(this, arg);
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
	}

	@Override
	public void visit(InitializerDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        n.getBlock().accept(this, arg);
	}

	@Override
	public void visit(JavadocComment n, Object arg) {
	}

	@Override
	public void visit(ClassOrInterfaceType n, Object arg) {
		if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }
	}

	@Override
	public void visit(PrimitiveType n, Object arg) {
	}

	@Override
	public void visit(ReferenceType n, Object arg) {
		n.getType().accept(this, arg);
	}

	@Override
	public void visit(VoidType n, Object arg) {
	}

	@Override
	public void visit(WildcardType n, Object arg) {
        if (n.getExtends() != null) {
            n.getExtends().accept(this, arg);
        }
        if (n.getSuper() != null) {
            n.getSuper().accept(this, arg);
        }
	}

	@Override
	public void visit(ArrayAccessExpr n, Object arg) {
		n.getName().accept(this, arg);
        n.getIndex().accept(this, arg);
	}

	@Override
	public void visit(ArrayCreationExpr n, Object arg) {
        n.getType().accept(this, arg);

        if (n.getDimensions() != null) {
            for (Expression dim : n.getDimensions()) {
                dim.accept(this, arg);
            }
        } else {
            n.getInitializer().accept(this, arg);
        }
	}

	@Override
	public void visit(ArrayInitializerExpr n, Object arg) {
        if (n.getValues() != null) {
            for (Iterator<Expression> i = n.getValues().iterator(); i.hasNext();) {
                Expression expr = i.next();
                expr.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(AssignExpr n, Object arg) {
		Scope currentScope = (Scope) n.getData();
		
		Symbol variableSym = currentScope.resolve(n.getTarget().toString());
		
		if(variableSym == null){
			throw new A2SemanticsException(n.getTarget().toString() + " is not defined on line " + n.getBeginLine());
		}
		
		symtab.Type typeOfExpression = getTypeOfExpression(n.getValue(), n.getData());
		if (typeOfExpression == null) {
			throw new A2SemanticsException("Expression type is not valid on line " + n.getBeginLine());
		}
		if ((symtab.Type) variableSym.getType() != typeOfExpression) {
			throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to " + variableSym.getType().getName()
					+ " on line " + n.getBeginLine());
		}
		
		n.getTarget().accept(this, arg);
        n.getValue().accept(this, arg);
	}

	@Override
	public void visit(BinaryExpr n, Object arg) {
		n.getLeft().accept(this, arg);
		n.getRight().accept(this, arg);
	}

	@Override
	public void visit(CastExpr n, Object arg) {
        n.getType().accept(this, arg);
        n.getExpr().accept(this, arg);
	}

	@Override
	public void visit(ClassExpr n, Object arg) {
		n.getType().accept(this, arg);
	}

	@Override
	public void visit(ConditionalExpr n, Object arg) {
		n.getCondition().accept(this, arg);
        n.getThenExpr().accept(this, arg);
        n.getElseExpr().accept(this, arg);
	}

	@Override
	public void visit(EnclosedExpr n, Object arg) {
		n.getInner().accept(this, arg);
	}

	@Override
	public void visit(FieldAccessExpr n, Object arg) {
		 n.getScope().accept(this, arg);
	}

	@Override
	public void visit(InstanceOfExpr n, Object arg) {
		n.getExpr().accept(this, arg);
        n.getType().accept(this, arg);
	}

	@Override
	public void visit(StringLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(IntegerLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(LongLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, Object arg) {
	}

	@Override
	public void visit(LongLiteralMinValueExpr n, Object arg) {
	}

	@Override
	public void visit(CharLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(DoubleLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(BooleanLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(NullLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(MethodCallExpr n, Object arg) {
		if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(NameExpr n, Object arg) {
	}

	@Override
	public void visit(ObjectCreationExpr n, Object arg) {
		if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }

        n.getType().accept(this, arg);

        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(QualifiedNameExpr n, Object arg) {
		n.getQualifier().accept(this, arg);
	}

	@Override
	public void visit(SuperMemberAccessExpr n, Object arg) {
	}

	@Override
	public void visit(ThisExpr n, Object arg) {
		if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
        }
	}

	@Override
	public void visit(SuperExpr n, Object arg) {
		if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
        }
	}

	@Override
	public void visit(UnaryExpr n, Object arg) {
		n.getExpr().accept(this, arg);
	}

	@Override
	public void visit(VariableDeclarationExpr n, Object arg) {
		// Check that the type of the variable is valid (i.e., a class)
		Type type = n.getType();

		Symbol variableTypeSym = ((Scope) n.getData()).resolve(type.toString());
		checkType(type, variableTypeSym);

		for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext();) {
			// TODO potentially refactor this and FieldDeclarator into
			// variableDeclarator visitor
			VariableDeclarator v = i.next();
			System.out.println(v.getId() + " has expression: " + v.getInit());

			// Checks expression type is the same as variable type
			if (v.getInit() != null) {
				symtab.Type typeOfExpression = getTypeOfExpression(v.getInit(), n.getData());
				if (typeOfExpression == null) {
					throw new A2SemanticsException("Expression type is not valid on line " + v.getBeginLine());
				}
				if ((symtab.Type) variableTypeSym != typeOfExpression) {
					throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to " + type
							+ " on line " + type.getBeginLine());
				}
			}

			// Checks variable name hasn't been defined
			checkName(n.getData(), v.getId());

			// Add variable symbol to scope
			VariableSymbol variableSym = new VariableSymbol(v.getId().getName(), (symtab.Type) variableTypeSym);
			((Scope) n.getData()).define(variableSym);
		}
	}

	/**
	 * Checks if the name of a variable (including field) is valid, if so, add
	 * to current scope
	 * 
	 * @param type
	 *            Type of the variable
	 * @param declaratorId
	 *            The declaration ID
	 */
	private void checkName(Object currentScope, VariableDeclaratorId declaratorId) {
		Symbol declaratorIdSymbol = ((ScopedSymbol) currentScope).resolveThisLevel(declaratorId.toString());
		if (declaratorIdSymbol != null) {
			throw new A2SemanticsException(declaratorId + " on line " + declaratorId.getBeginLine()
					+ " is already defined. Try another name!");
		}
	}

	private symtab.Type getTypeOfExpression(Expression expression, Object currentScope) {
		symtab.Type type = null;
		if (expression != null) {
			if (expression.getClass() == NameExpr.class) {
				// System.out.println(expression.toString() + ": named expression ");
				Symbol expressionTypeSym = ((Scope) currentScope).resolve(expression.toString());

				if (expressionTypeSym == null) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not defined");
				}
				if (!(expressionTypeSym.getType() instanceof symtab.Type)) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not valid");
				}

				type = expressionTypeSym.getType();
			} else if(expression.getClass() == ObjectCreationExpr.class){
				// TODO refactor into method with the if above
				Symbol expressionTypeSym = ((Scope) currentScope).resolve(((ObjectCreationExpr) expression).getType().toString());
				
				if (expressionTypeSym == null){
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not defined");
				}
				if (!(expressionTypeSym instanceof symtab.ClassOrInterfaceSymbol)) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not valid");
				}
				// Checks that the expression is a class, not interface
				if(((ClassOrInterfaceSymbol) expressionTypeSym).isInterface()){
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not a class");
				}
				type = (symtab.Type) expressionTypeSym;
			} else {
				if (expression.getClass() == IntegerLiteralExpr.class) {
					type = (symtab.Type) ((Scope) currentScope).resolve("int");
				} else if (expression.getClass() == StringLiteralExpr.class) {
					type = (symtab.Type) ((Scope) currentScope).resolve("String");
				} else if (expression.getClass() == BooleanLiteralExpr.class){
					type = (symtab.Type) ((Scope) currentScope).resolve("boolean");
				}
				// TODO other primitive types (and others?)
				else {
					System.out.println("Add " + expression.getClass() + " to getTypeofExpression helper method");
				}
			}
		}
		return type;
	}

	private void checkType(Object type, Symbol symOfVarType) {
		if (symOfVarType == null) {
			throw new A2SemanticsException(
					type + " on line " + ((Node) type).getBeginLine() + " is not a defined type");
		}

		if (!(symOfVarType instanceof symtab.Type)) {
			// System.out.println(symOfVariableType.getClass());
			throw new A2SemanticsException(type + " on line " + ((Node) type).getBeginLine() + " is not a valid type");
		}
	}

	@Override
	public void visit(MarkerAnnotationExpr n, Object arg) {
		n.getName().accept(this, arg);
	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, Object arg) {
        n.getName().accept(this, arg);
        n.getMemberValue().accept(this, arg);
	}

	@Override
	public void visit(NormalAnnotationExpr n, Object arg) {
        n.getName().accept(this, arg);
        for (Iterator<MemberValuePair> i = n.getPairs().iterator(); i.hasNext();) {
            MemberValuePair m = i.next();
            m.accept(this, arg);
        }
	}

	@Override
	public void visit(MemberValuePair n, Object arg) {
		n.getValue().accept(this, arg);
	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
		if (!n.isThis()) {
            if (n.getExpr() != null) {
                n.getExpr().accept(this, arg);
            }
        }
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        }
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
		n.getTypeDeclaration().accept(this, arg);
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
		n.getCheck().accept(this, arg);
		if (n.getMessage() != null) {
			n.getMessage().accept(this, arg);
		}
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		System.out.println("BlockStmt");
		if(n.getStmts()!=null){
			for (Statement statement : n.getStmts()) {
				statement.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(LabeledStmt n, Object arg) {
		n.getStmt().accept(this, arg);
	}

	@Override
	public void visit(EmptyStmt n, Object arg) {
	}

	@Override
	public void visit(ExpressionStmt n, Object arg) {
		n.getExpression().accept(this, arg);
	}

	@Override
	public void visit(SwitchStmt n, Object arg) {
		n.getSelector().accept(this, arg);
		if (n.getEntries() != null) {
			for (SwitchEntryStmt e : n.getEntries()) {
				e.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(SwitchEntryStmt n, Object arg) {
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
	}

	@Override
	public void visit(ReturnStmt n, Object arg) {
		if (n.getExpr() != null) {
			n.getExpr().accept(this, arg);
		}
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		n.getCondition().accept(this, arg);
		n.getThenStmt().accept(this, arg);
		if (n.getElseStmt() != null) {
			n.getElseStmt().accept(this, arg);
		}
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		n.getCondition().accept(this, arg);
		n.getBody().accept(this, arg);

	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
	}

	@Override
	public void visit(DoStmt n, Object arg) {
		n.getBody().accept(this, arg);
		n.getCondition().accept(this, arg);
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		n.getVariable().accept(this, arg);
		n.getIterable().accept(this, arg);
		n.getBody().accept(this, arg);
	}

	@Override
	public void visit(ForStmt n, Object arg) {
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