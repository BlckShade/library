/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity;

/**
 *
 * @author Shadow
 */
public class unite extends Object{
    private Object[] o;
    public Books b;
    public Authors a;
    public unite() 
    {      
    }
    public void convert()
    {
        this.b = (Books) o[0];
        this.a = (Authors) o[1];
    }
    
}
