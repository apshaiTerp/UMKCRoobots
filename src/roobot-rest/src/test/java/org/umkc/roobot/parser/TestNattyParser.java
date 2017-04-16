package org.umkc.roobot.parser;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * @author AC010168
 *
 */
public class TestNattyParser {
  
  public final static long dateValue = 1492214881936L;
  
  @Test
  public void testBasicParsing() {
    System.out.println ("**** testBasicParsing() ****");
    String inputText = "the day before next thursday";

    int results = processTextForDates(inputText);
    
    assertEquals("I should only find one date", 1, results);
    assertEquals("The world is ending", 1, 1);
  }

  @Test
  public void testBasicParsing2() {
    System.out.println ("**** testBasicParsing2() ****");
    String inputText = "I would like to meet with you tomorrow.";

    int results = processTextForDates(inputText);
    
    assertEquals("I should only find one date", 1, results);
    assertEquals("The world is ending", 1, 1);
  }

  @Test
  public void testBasicParsing3() {
    System.out.println ("**** testBasicParsing3() ****");
    String inputText = "If that does not work for you, I can stop by 4/17/2016 at 5pm.";
    
    int results = processTextForDates(inputText);
    
    assertEquals("I should only find one date", 1, results);
    assertEquals("The world is ending", 1, 1);
  }
  
  @Test
  public void testBasicParsing4() {
    System.out.println ("**** testBasicParsing4() ****");
    String inputText = "I'd like to meet today, but if not, I can probably meet tomorrow as well.";
    
    int results = processTextForDates(inputText);
    
    assertEquals("I should only find two dates", 2, results);
    assertEquals("The world is ending", 1, 1);
  }
  
  @Test
  public void testAssemblyForDate1() {
    System.out.println ("**** testAssemblyForDate1() ****");
    String inputText = "I'd like to meet today, but if not, it's fine.";
    
    String results = assembleDateString(inputText);
    System.out.println ("Final Results: " + results);

    assertEquals("The world is ending", 1, 1);
  }
  
  @Test
  public void testAssemblyForDate2() {
    System.out.println ("**** testAssemblyForDate2() ****");
    String inputText = "This is just a plain old boring sentence.";
    
    String results = assembleDateString(inputText);
    System.out.println ("Final Results: " + results);

    assertEquals("The world is ending", 1, 1);
  }
  
  @Test
  public void testAssemblyForDate3() {
    System.out.println ("**** testAssemblyForDate3() ****");
    String inputText = "I'd like to meet today, but if not, tomorrow is fine.";
    
    String results = assembleDateString(inputText);
    System.out.println ("Final Results: " + results);

    assertEquals("The world is ending", 1, 1);
  }
  
  @Test
  public void testAssemblyForDate4() {
    System.out.println ("**** testAssemblyForDate4() ****");
    String inputText = "I'd like to meet today, but if not, tomorrow is fine.\n\n\tStuff here.\n\tSome more stuff here.\n\nThis is the last line, and I need to meet Tuesday at 9 am.";
    
    String results = assembleDateString(inputText);
    System.out.println ("Final Results 4: " + results);

    assertEquals("The world is ending", 1, 1);
  }
  
  private int processTextForDates(String inputText) {
    Parser parser = new Parser();
    System.out.println ("inputText: " + inputText);

    List<DateGroup> groups = parser.parse(inputText);
    
    System.out.println ("Number of Date Groups: " + groups.size());
    for (DateGroup group : groups) {
      System.out.println ("-- Processing a New Date --");
      List<Date> date = group.getDates();
      System.out.println ("  Number of Dates: " + date.size());
      for (Date aDate : date)
        System.out.println ("    date: " + aDate);
      
      //int line    = group.getLine();
      int column  = group.getPosition();
      //int column2 = group.getAbsolutePosition();
      //System.out.println ("  line:    " + line);
      System.out.println ("  column:  " + column);
      //System.out.println ("  column2: " + column2);
      
      String prefix = group.getPrefix(inputText.length());
      String suffix = group.getSuffix(inputText.length());
      System.out.println ("  prefix: " + prefix);
      System.out.println ("  suffix: " + suffix);
      
      String matchingValue = group.getText();
      //String syntaxTree = group.getSyntaxTree().toStringTree();
      System.out.println ("  matchingValue: " + matchingValue);
      //System.out.println ("  syntaxTree:    " + syntaxTree);
      
      //Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
      //System.out.println ("  parseMap Size: " + parseMap.size());
      //for (String key : parseMap.keySet())
      //  System.out.println ("    parseLocation: " + parseMap.get(key));
      
      boolean isRecurring = group.isRecurring();
      boolean isInferred1 = group.isDateInferred();
      boolean isInferred2 = group.isTimeInferred();
      System.out.println ("  isRecurring:    " + isRecurring);
      System.out.println ("  isDateInferred: " + isInferred1);
      System.out.println ("  isTimeInferred: " + isInferred2);
      
      Date recursUntil = group.getRecursUntil();
      System.out.println ("  recursUntil: " + recursUntil);
    }
    
    return groups.size();
  }
  
  private String assembleDateString(String inputText) {
    String answer = "";
    
    Parser parser = new Parser();
    System.out.println ("inputText: " + inputText);

    List<DateGroup> groups = parser.parse(inputText);
    //Check default state
    if (groups.size() == 0) return inputText;
    
    while (groups.size() > 0) {
      System.out.println ("groups.size(): " + groups.size());
      DateGroup group = groups.get(0);
      
      List<Date> date = group.getDates();
      System.out.println ("  Number of Dates: " + date.size());
      for (Date aDate : date)
        System.out.println ("    date: " + aDate);
      
      String matchingValue = group.getText();
      String prefix = group.getPrefix(inputText.length());
      String suffix = group.getSuffix(inputText.length());
      System.out.println ("  matchingValue: " + matchingValue);
      System.out.println ("  prefix:        " + prefix);
      System.out.println ("  suffix:        " + suffix);

      answer += prefix + "<a href=\"#\" style=\"text-decoration:none;display:block,inline;\">" + 
        "<span style=\"white-space:nowrap\" onclick=\"javascript:alert(1)\">" + matchingValue + "</span></a>";
      
      //If this is the last group, grab the suffix and finish the sentence.
      if (groups.size() == 1) {
        answer += suffix;
        break;
      } else {
        //'Iterate' through the string, rather than deal with subsection madness
        groups = parser.parse(suffix);
      }
    }
    return answer;
  }
}
