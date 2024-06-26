package ut.ru.samokat.atlassian.jira.tutorials.historywriter;

import org.junit.Test;
import ru.samokat.atlassian.jira.tutorials.historywriter.api.MyPluginComponent;
import ru.samokat.atlassian.jira.tutorials.historywriter.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest {
    @Test
    public void testMyName() {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent", component.getName());
    }
}