package com.github.gtache.lsp.its;

import com.github.gtache.lsp.client.languageserver.serverdefinition.ExeLanguageServerDefinition;
import com.github.gtache.lsp.client.languageserver.serverdefinition.LanguageServerDefinition;
import com.github.gtache.lsp.client.languageserver.serverdefinition.UserConfigurableServerDefinition;
import com.github.gtache.lsp.settings.LSPState;
import com.github.gtache.lsp.utils.TestUtils;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompletionTest extends LightCodeInsightFixtureTestCase {
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    Path languageServer = Paths.get(".", "testServer", "build", "libs", "testServer.jar");

    List<String> args = new ArrayList<>();
    args.add("-jar");
    args.add(languageServer.toString());
    args.add("-Xdebug");
    args.add("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1044");
    ExeLanguageServerDefinition definition = new ExeLanguageServerDefinition("test", "java", args.toArray(new String[0]));

    LSPState state = LSPState.getInstance();
    Map<String, UserConfigurableServerDefinition> servers = new HashMap<>();
    servers.put("test", definition);
    state.setExtToServ(servers);
  }

  @Override
  protected String getTestDataPath() {
    return TestUtils.BASE_TEST_DATA_PATH;
  }

  public void testCompletion() {
    myFixture.configureByFiles("test1.test", "test2.test");
    myFixture.complete(CompletionType.BASIC, 1);
    List<String> strings = myFixture.getLookupElementStrings();
    assertTrue(strings.containsAll(Arrays.asList("key with spaces", "language", "message", "tab", "website")));
    assertEquals(5, strings.size());
  }
}