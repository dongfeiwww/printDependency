import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

public class GraphTest {
  @Test
  public void testNormal() {
    List<String> inputs = new ArrayList<String>();
    inputs.add("myclass jquery jquery.cookie underscore base");
    inputs.add("jquery base");
    inputs.add("jquery.cookie jquery base");
    inputs.add("underscore jquery base");
    try {
      List<String> output = new Graph().topologicalSort(inputs);
      Assert.assertEquals("output is not correct",
          "[myclass, jquery.cookie, underscore, jquery, base]",
          output.toString());
    } catch (Exception e) {
      fail("Exception" + e.getMessage() + " found when sorting.");
    }
  }

  @Test
  public void testSimple() {
    List<String> inputs = new ArrayList<String>();
    inputs.add("a c b");
    inputs.add("c b");
    try {
      List<String> output = new Graph().topologicalSort(inputs);
      Assert.assertEquals("output is not correct", "[a, c, b]", output.toString());
    } catch (Exception e) {
      fail("Exception" + e.getMessage() + " found when sorting.");
    }
  }

  @Test
  public void testCycle() {
    List<String> inputs = new ArrayList<String>();
    inputs.add("a b");
    inputs.add("b a");
    try {
      List<String> output = new Graph().topologicalSort(inputs);
      Assert.assertTrue("Should throw exception", true);
    } catch (Exception e) {
    }
  }

  @Test
  public void testSingle() {
    List<String> inputs = new ArrayList<String>();
    inputs.add("a");
    try {
      List<String> output = new Graph().topologicalSort(inputs);
      Assert.assertEquals("output is not correct", "[a]", output.toString());
    } catch (Exception e) {
      fail("Exception" + e.getMessage() + " found when sorting.");
    }
  }
}
