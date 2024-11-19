package be.kevin.kernel;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.javadoc.JavadocBlockTag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class Main {
	
	public Main() {		
		try {
			JavaParser jp = new JavaParser();
            // Lire le fichier source Java
            File file = new File("C:\\Users\\externe\\eclipse-workspace\\DoisJeVersioner\\src\\be\\kevin\\git\\Git.java");

            // Analyse du fichier source avec JavaParser
            ParseResult<CompilationUnit> result;
			try {
				result = jp.parse(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

            // Vérifier si l'analyse a réussi
            if (result.isSuccessful()) {
                this.parsing(result.getResult().get());
                
            } else {
                System.out.println("Erreur d'analyse du fichier Java");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void parsing(CompilationUnit cu) {
        // Extraire et afficher les classes/interfaces
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterface -> {
            System.out.println("Classe : " + classOrInterface.getName());
            
            // Extraire les méthodes
            classOrInterface.findAll(MethodDeclaration.class).forEach(method -> {
                //System.out.println("Méthode : " + method.getName());
                List<Comment> comments = method.getAllContainedComments();
                for (Comment comment : comments) {
					//System.out.println(comment.getContent());
				}
                
            });
            
            System.out.println();
            
            
            
            classOrInterface.findAll(MethodDeclaration.class).forEach(methodDeclaratation -> {
            	System.out.println("Declaration : " + methodDeclaratation.getDeclarationAsString(true, true, true));
            	for (Parameter parameter : methodDeclaratation.getParameters()) {
					System.out.println("Parameter inside : " + parameter.getNameAsString());
				}
            	
            	System.out.println("Begin line : " + methodDeclaratation.getRange().get().begin.line);
            	System.out.println("End line : " + methodDeclaratation.getRange().get().end.line);
            	
            	JavadocComment javadocComment = methodDeclaratation.getJavadocComment().get();
            	System.out.println(javadocComment.asString());
            	for (JavadocBlockTag block : methodDeclaratation.getJavadoc().get().getBlockTags()) {
					System.out.println(block.getTagName());
					System.out.println(block.getName().get().toString());
				}
            	System.out.println();
            });
            
            classOrInterface.findAll(Parameter.class).forEach(paramater -> {
            	System.out.println("Parameter : " + paramater.getNameAsString());
            });
            
            classOrInterface.findAll(ReceiverParameter.class).forEach(receiveParameter -> {
            	System.out.println("ReceiverParameter : " + receiveParameter.getNameAsString());
            });
            
            classOrInterface.findAll(ConstructorDeclaration.class).forEach(constructorDeclaration -> {
            	System.out.println("ConstructorDeclaration : " + constructorDeclaration.getDeclarationAsString(true, true));
            });

            classOrInterface.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
                for (VariableDeclarator variable : fieldDeclaration.getVariables()) {
					System.out.println("Variable : " + variable.getNameAsString());
				}
            });
            
        });

        // Extraire et afficher les commentaires
        cu.getAllComments().forEach(comment -> {
            //this.methodWithComment(cu, comment);
        });
        //System.out.println(cu.toString());
	}
	
	private void methodWithComment(CompilationUnit cu, Comment comment) {
        int commentLine = comment.getRange().get().begin.line;

        // Parcourir toutes les méthodes pour vérifier si le commentaire se trouve dans l'une d'elles
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            int methodStartLine = method.getRange().get().begin.line;
            int methodEndLine = method.getRange().get().end.line;
            
            // Si la ligne du commentaire est entre la ligne de début et la ligne de fin de la méthode
            if (commentLine >= methodStartLine && commentLine <= methodEndLine) {
                //System.out.println("Le commentaire appartient à la méthode : " + method.getName());
                //System.out.println("Commentaire : " + comment.getContent());
            }
        });
    }

	public static void main(String[] args) {
		new Main();
		
	}

}
