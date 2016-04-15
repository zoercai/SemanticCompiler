/*
 * Copyright (C) 2007 J�lio Vilmar Gesser.
 * 
 * This file is part of Java 1.5 parser and Abstract Syntax Tree.
 *
 * Java 1.5 parser and Abstract Syntax Tree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java 1.5 parser and Abstract Syntax Tree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java 1.5 parser and Abstract Syntax Tree.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 05/10/2006
 */
package japa.parser.ast.expr;

import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.List;

/**
 * @author Julio Vilmar Gesser
 */
public final class HashMapCreationExpr extends Expression {

    private final Type type;

    private final List<Type> typeArgs;

    private final HashMapInitializerExpr initializer;

    private String keyType;
    private String valueType;
    
    
    public HashMapCreationExpr(int line, int column, Type type, List<Type> typeArgs, HashMapInitializerExpr initializer) {
        super(line, column);
        this.type = type;
        this.typeArgs = typeArgs;
        this.initializer = initializer;
    }

    public Type getType() {
        return type;
    }

    public List<Type> getTypeArgs() {
        return typeArgs;
    }

    public HashMapInitializerExpr getInitializer() {
        return initializer;
    }

    public HashMapCreationExpr(int line, int column, Type type, List<Type> typeArgs) {
        super(line, column);
        this.type = type;
        this.typeArgs = typeArgs;
        this.initializer = null;
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
