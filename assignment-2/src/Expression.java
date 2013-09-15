/**
 * Created By: Alex Vallejo
 * Date: 9/14/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

public class Expression {

    private boolean value;
    private String line;

    public Expression(String line){

        this.line = line;

        char chars[] = line.toCharArray();

        for (char c : chars){
            c = Character.toUpperCase(c);
        }

        if (isNotValid(line))
            return;

    }

    public static void setAtom(String atom, String value){
    }

    public boolean evaluate(){

        //ToDo set value to evulation
        return false;
    }

    public Expression copy(){
        return new Expression(line);
    }

    public void normalize(){
    }

    public void displayNormalized(){
    }

    public String toString(){
        return line + " = " + this.value;
    }

    private boolean isNotValid(String line){
        char[] chars = line.toCharArray();

        //ToDo validate the expression and print a helpful error message if the expression is not valid

        return true;
    }
}
