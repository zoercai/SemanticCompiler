package japa.parser.ast.visitor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import japa.parser.ast.expr.HashMapCreationExpr;
import japa.parser.ast.expr.HashMapInitializerExpr;
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
import japa.parser.ast.expr.UnaryExpr.Operator;
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

public class CheckTerminalVisitor implements VoidVisitor<Object> {

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

		for (Iterator<VariableDeclarator> i = n.getVariables().iterator(); i.hasNext();) {
			VariableDeclarator v = i.next();

			// Checks expression type is the same as field type
			if (v.getInit() != null) {
				symtab.Type typeOfExpression = getTypeOfExpression(v.getInit(), n.getData(), v.getBeginLine(),
						v.getBeginColumn());
				if (typeOfExpression == null) {
					throw new A2SemanticsException("Expression type is not valid on line " + v.getBeginLine());
				}
				if ((symtab.Type) fieldTypeSym != typeOfExpression) {
					throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to " + type
							+ " on line " + type.getBeginLine());
				}
			}
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

		if (!n.getTarget().toString().contains(".")) {
			// Checks that the variable exists
			Symbol variableSym = currentScope.resolve(n.getTarget().toString());
			if (variableSym == null) {
				throw new A2SemanticsException(
						n.getTarget().toString() + " is not defined on line " + n.getBeginLine());
			}

			// Checks that the expression variable type matches the variable
			// type
			symtab.Type typeOfExpression = getTypeOfExpression(n.getValue(), n.getData(), n.getBeginLine(),
					n.getBeginColumn());
			if (typeOfExpression == null) {
				throw new A2SemanticsException("Expression type is not valid on line " + n.getBeginLine());
			}
			if ((symtab.Type) variableSym.getType() != typeOfExpression) {
				throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to "
						+ variableSym.getType().getName() + " on line " + n.getBeginLine());
			}
		} else {
			String[] parts = n.getTarget().toString().split("\\.");

			// Check first part
			Symbol firstSymbol = currentScope.resolve(parts[0]);
			if (firstSymbol == null) {
				throw new A2SemanticsException(parts[0] + " is not defined on line " + n.getBeginLine());
			}

			Symbol classSymbol = null;

			if (firstSymbol instanceof VariableSymbol) {
				// If first part is a variable
				// Check var is type class and is initialised
				classSymbol = currentScope.resolve(firstSymbol.getType().getName());
				if (!(classSymbol instanceof ClassOrInterfaceSymbol)) {
					throw new A2SemanticsException(
							firstSymbol.getName() + " on line " + n.getBeginLine() + "is not a valid type");
				}
			} else if (firstSymbol instanceof ClassOrInterfaceSymbol) {
				// If first part is a class
				classSymbol = firstSymbol;
			} else {
				throw new A2SemanticsException(
						firstSymbol.getName() + " on line " + n.getBeginLine() + "is not a valid type");
			}

			// TODO first part == this

			// Check the second part

			// If second part is a field
			// Get the class symbol of the variable class
			Symbol fieldSymbol = ((ClassOrInterfaceSymbol) classSymbol).resolveMember(parts[1]);
			if (fieldSymbol == null) {
				throw new A2SemanticsException(parts[1] + " is not defined on line " + n.getBeginLine());
			}

			// TODO more parts

			// Checks that the expression variable type matches the variable
			// type
			symtab.Type typeOfExpression = getTypeOfExpression(n.getValue(), n.getData(), n.getBeginLine(),
					n.getBeginColumn());
			if (typeOfExpression == null) {
				throw new A2SemanticsException("Expression type is not valid on line " + n.getBeginLine());
			}
			// TODO assuming only two parts for now
			if ((symtab.Type) fieldSymbol.getType() != typeOfExpression) {
				throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to "
						+ fieldSymbol.getType().getName() + " on line " + n.getBeginLine());
			}
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
		checkMethodCall(n);

		if (n.getArgs() != null) {
			// Check if method call arguments exist and types are consistent
			// with method parameters

			for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
				Expression e = i.next();

				e.accept(this, arg);
			}
		}
	}

	/**
	 * @param n
	 */
	private void checkMethodCall(MethodCallExpr expression) {
		Scope currentScope = (Scope) expression.getData();

		// Check if method exists
		Symbol methodSym = currentScope.resolve(expression.getName());
		if (methodSym == null) {
			throw new A2SemanticsException(expression + " on line " + expression.getBeginLine() + " is not defined");
		}

		// Check if method call arguments exist and types are consistent with
		// method parameters
		List<Parameter> methodParameters = ((MethodSymbol) methodSym).getParameters();
		List<Expression> methodArguments = expression.getArgs();
		if (methodParameters != null && methodArguments != null) {
			// Check number of arguments & parameters are the same
			if (methodParameters.size() != methodArguments.size()) {
				throw new A2SemanticsException(
						"Method call doesn't match method signature on line " + expression.getBeginLine());
			}
			// Check types are the same
			for (int i = 0; i < methodParameters.size(); i++) {
				Expression argumentExpression = expression.getArgs().get(i);
				Parameter parameter = methodParameters.get(i);

				// Check argument name exists (by calling getTypeOfExpression,
				// which also returns the type), and checks if types match
				if (!getTypeOfExpression(argumentExpression, currentScope, argumentExpression.getBeginLine(),
						argumentExpression.getBeginColumn()).getName().equals(parameter.getType().toString())) {
					throw new A2SemanticsException("Method call argument type doesn't match method signature on line "
							+ expression.getBeginLine());
				}
			}
		} else if (methodParameters == null && methodArguments == null) {
			// do nothing
		} else {
			throw new A2SemanticsException(
					"Method call doesn't match method signature on line " + expression.getBeginLine());
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
		Scope currentScope = (Scope) n.getData();
		Type type = n.getType();

		Symbol variableTypeSym = currentScope.resolve(type.toString());
		checkType(type, variableTypeSym);

		for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext();) {
			// TODO potentially refactor this and FieldDeclarator into
			// variableDeclarator visitor
			VariableDeclarator v = i.next();

			if (v.getInit() != null) {
				// Checks expression type is the same as variable type
				symtab.Type typeOfExpression = getTypeOfExpression(v.getInit(), n.getData(), v.getBeginLine(),
						v.getBeginColumn());

				if (typeOfExpression == null) {
					throw new A2SemanticsException("Expression type is not valid on line " + v.getBeginLine());
				}

				// Checks that the types are the same (or in the case of null,
				// allowed to be assigned)
				if (((symtab.Type) variableTypeSym != typeOfExpression)) {

					if (!((variableTypeSym.getName().equals("String")
							|| variableTypeSym instanceof ClassOrInterfaceSymbol)
							&& typeOfExpression.getName().equals("null"))
							&& !(variableTypeSym.getClass() == ClassOrInterfaceSymbol.class
									&& typeOfExpression.getClass() == ClassOrInterfaceSymbol.class
									&& (((ClassOrInterfaceSymbol) typeOfExpression).getImplementsList()
											.contains(variableTypeSym)
											|| ((ClassOrInterfaceSymbol) typeOfExpression)
													.resolveParent(variableTypeSym.getName()) != null)))
					{
						throw new A2SemanticsException("Cannot convert from " + typeOfExpression.getName() + " to "
								+ type + " on line " + type.getBeginLine());
					}
				}
				
				v.getInit().accept(this, arg);
			}
		}
	}

	/**
	 * Checks if the name of a variable (or a field) has been used, will throw
	 * an exception if it has
	 * 
	 * @param declaratorId
	 *            Name of variable
	 */
	private void checkName(Object currentScope, VariableDeclaratorId declaratorId) {
		Symbol declaratorIdSymbol = ((ScopedSymbol) currentScope).resolveThisLevel(declaratorId.toString());
		if (declaratorIdSymbol != null) {
			throw new A2SemanticsException(declaratorId + " on line " + declaratorId.getBeginLine()
					+ " is already defined. Try another name!");
		}
	}

	private symtab.Type getTypeOfExpression(Expression expression, Object scope, int beginLine, int beginColumn) {
		symtab.Type type = null;
		Scope currentScope = (Scope) scope;

		if (expression != null) {
			if (expression.getClass() == NameExpr.class) {
				// Check that the expression variable is defined and that its
				// type is a class
				Symbol expressionSymbol = currentScope.resolve(expression.toString());
				if (expressionSymbol == null) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not defined");
				}
				if (!(expressionSymbol.getType() instanceof symtab.Type)) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not a valid type");
				}

				// If expression variable/field is from the same scope level or
				// if expression is from a higher level but is a variable, check
				// it is declared and assigned a value before current line
				if (currentScope.resolveThisLevel(expression.toString()) != null
						|| !((VariableSymbol) expressionSymbol).isField()) {
					// Checks that the expression variable has been assigned a
					// value
					if (((VariableSymbol) expressionSymbol).getLineAssigned() == -1) {
						throw new A2SemanticsException("Variable \"" + expressionSymbol.getName()
								+ "\" has not been assigned a value on line " + expression.getBeginLine());
					}

					// If expression is assigned on a later line to being used
					if (beginLine < ((VariableSymbol) expressionSymbol).getLineAssigned()) {
						throw new A2SemanticsException(expression.toString() + " on line " + expression.getBeginLine()
								+ " is not assigned a value prior to this line");
					} else {
						// If expression is assigned on the same line as being
						// used but after being used
						if (beginLine == ((VariableSymbol) expressionSymbol).getLineAssigned()
								&& beginColumn < ((VariableSymbol) expressionSymbol).getColumnAssigned()) {
							throw new A2SemanticsException(expression.toString() + " on line "
									+ expression.getBeginLine() + " is not assigned a value prior to its column");
						}
					}
				}

				// Checks that the expression variable (not field, as implicit
				// value) has been assigned a value
				if ((!((VariableSymbol) expressionSymbol).isField())
						&& ((VariableSymbol) expressionSymbol).getValueAssigned() == null) {

				}

				type = expressionSymbol.getType();
			} else if (expression.getClass() == ObjectCreationExpr.class) {
				// If it's a constructor, check that it's constructing a class
				Symbol expressionTypeSym = currentScope.resolve(((ObjectCreationExpr) expression).getType().toString());

				// Checks that the constructor class has been defined
				if (expressionTypeSym == null) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not defined");
				}
				// Checks that the expression is a class type
				if (!(expressionTypeSym instanceof symtab.ClassOrInterfaceSymbol)) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not valid");
				}
				// Checks that the expression is not an interface
				if (((ClassOrInterfaceSymbol) expressionTypeSym).isInterface()) {
					throw new A2SemanticsException(
							expression + " on line " + expression.getBeginLine() + " is not a class");
				}

				type = (symtab.Type) expressionTypeSym;
			} else if (expression.getClass() == MethodCallExpr.class) {
				// If it's a method call, check against the method and return
				// method type

				Symbol methodSym = currentScope.resolve(((MethodCallExpr) expression).getName());

				checkMethodCall((MethodCallExpr) expression);

				type = (symtab.Type) currentScope.resolve(((MethodSymbol) methodSym).getReturnType().toString());

			} else if (expression.getClass() == BinaryExpr.class) {
				// System.out.println(expression);
				// If it's an operation expression
				// System.out.println("Binary expression on line: " + expression.getBeginLine());
				// Check left and right expressions are of the same type
				symtab.Type leftType = getTypeOfExpression(((BinaryExpr) expression).getLeft(), currentScope,
						expression.getBeginLine(), expression.getBeginColumn());
				symtab.Type rightType = getTypeOfExpression(((BinaryExpr) expression).getRight(), currentScope,
						expression.getBeginLine(), expression.getBeginColumn());
				if (leftType != rightType) {
					throw new A2SemanticsException("Cannot add " + leftType.getName() + " to " + rightType.getName()
							+ " on line " + expression.getBeginLine());
				}
				type = leftType;
			} else if (expression.getClass() == UnaryExpr.class) {
				type = getTypeOfExpression(((UnaryExpr) expression).getExpr(), currentScope, expression.getBeginLine(),
						expression.getBeginColumn());

				// Checks expression operand can be used with the operator
				Operator operator = ((UnaryExpr) expression).getOperator();
				if ((!isNumberClass(type)) && isNumberOperator(operator)) {
					throw new A2SemanticsException("Bad operand type " + type.getName() + " for unary operator "
							+ operator + " on line " + expression.getBeginLine());
				}
				if ((!type.getName().equals("boolean")) && operator == Operator.not) {
					throw new A2SemanticsException("Bad operand type " + type.getName() + " for unary operator "
							+ operator + " on line " + expression.getBeginLine());
				}
			} else if (expression.getClass() == HashMapCreationExpr.class) {

				type = (symtab.Type) currentScope.resolve("HashMap");

			} else {
				if (expression.getClass() == IntegerLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("int");
				} else if (expression.getClass() == BooleanLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("boolean");
				} else if (expression.getClass() == LongLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("long");
				} else if (expression.getClass() == CharLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("char");
				} else if (expression.getClass() == DoubleLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("double");
				} else if (expression.getClass() == NullLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("null");
				} else if (expression.getClass() == StringLiteralExpr.class) {
					type = (symtab.Type) currentScope.resolve("String");
				}
				// TODO other types
				else {
					// System.out.println("Add " + expression.getClass() + " to getTypeofExpression helper method");
				}
			}
		}
		return type;
	}

	private boolean isNumberClass(symtab.Type type) {
		if (type.getName().equals("int") || type.getName().equals("long") || type.getName().equals("char")
				|| type.getName().equals("double")) {
			return true;
		}
		return false;
	}

	private boolean isNumberOperator(Operator operator) {
		if (operator == Operator.positive || operator == Operator.negative || operator == Operator.preIncrement
				|| operator == Operator.preDecrement || operator == Operator.posIncrement
				|| operator == Operator.preDecrement) {
			return true;
		}
		return false;
	}

	private void checkType(Object type, Symbol symOfVarType) {
		if (symOfVarType == null) {
			throw new A2SemanticsException(
					type + " on line " + ((Node) type).getBeginLine() + " is not a defined type");
		}

		if (!(symOfVarType instanceof symtab.Type)) {
			// // System.out.println(symOfVariableType.getClass());
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
		if (n.getStmts() != null) {
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

	@Override
	public void visit(HashMapCreationExpr n, Object arg) {
		Scope currentScope = (Scope) n.getData();
		
		HashMapInitializerExpr mapContent = n.getInitializer();
		
		Map<Expression, Expression> map = mapContent.getValues();

		symtab.Type keyType = getTypeOfExpression(map.entrySet().iterator().next().getKey(), currentScope, n.getBeginLine(), n.getBeginColumn());
		symtab.Type valueType = getTypeOfExpression(map.entrySet().iterator().next().getValue(), currentScope, n.getBeginLine(), n.getBeginColumn());
		
		for (Map.Entry<Expression, Expression> entry : map.entrySet()) {
			symtab.Type currentKeyType = getTypeOfExpression(entry.getKey(), currentScope, n.getBeginLine(), n.getBeginColumn());
			symtab.Type currentValueType = getTypeOfExpression(entry.getValue(), currentScope, n.getBeginLine(), n.getBeginColumn());
			
			if(keyType!=currentKeyType){
				throw new A2SemanticsException("The type of " + entry.getKey() + " on line " + entry.getKey().getBeginLine() + " is not consistent with the rest in the map");
			}
			
			if(valueType!=currentValueType){
				throw new A2SemanticsException("The type of " + entry.getValue() + " on line " + entry.getValue().getBeginLine() + " is not consistent with the rest in the map");
			}
		}
		
		n.setKeyType(map.entrySet().iterator().next().getKey().getClass().getSimpleName().toString().split("Literal")[0]);
		n.setValueType(map.entrySet().iterator().next().getValue().getClass().getSimpleName().toString().split("Literal")[0]);
	}

	@Override
	public void visit(HashMapInitializerExpr n, Object arg) {

	}

}