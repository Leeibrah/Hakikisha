/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maptrust;

/**
 *
 * @author lee
 */
public class SystemUtils {
	/*
	 * Contains methods to perform routine system tasks like SQL command validations
	 */
	public enum StatusFlag { SUCCESS, FAILED, RESET };
	
	/*
	 * METHOD SUMMARY AND RETURN
	 * 		Evaluates whether an SQL statement has an invalid character in it such as "'", "/" , "\" or ";"
	 * 		and attempts to remove the illegal characters.
	 * 		
	 * 		Returns a string representation of the SQL Statement 
	 * 		that has been corrected for any invalid characters
	 */
	public static String sqlCommandFilter(String sqlCommand){
		/*** method not yet complete **/
		return sqlCommand;
	}
	
}
