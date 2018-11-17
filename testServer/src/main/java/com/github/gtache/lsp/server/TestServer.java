package com.github.gtache.lsp.server;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TestServer
  implements LanguageServer,
  LanguageClientAware,
  TextDocumentService,
  WorkspaceService {
  private final Position pos0 = new Position(0, 0);
  private final Range range0 = new Range(this.pos0, this.pos0);
  private final String[] commands = new String[]{"command1", "command2", "command3"};
  private final CompletionItem[] completionItems;
  private final List<String> uris;
  private final ServerCapabilities capabilities;
  private final TextDocumentSyncOptions syncKindOptions;
  private final Position[] positions;
  private final Range[] ranges;
  private LanguageClient client;
  private boolean willSaveWaitUntilCalled;

  @JsonNotification
  public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
    WorkspaceService.super.didChangeWorkspaceFolders(params);
  }

  @JsonRequest
  public CompletableFuture<List<? extends Location>> typeDefinition(TextDocumentPositionParams position) {
    return TextDocumentService.super.typeDefinition(position);
  }

  @JsonRequest
  public CompletableFuture<List<? extends Location>> implementation(TextDocumentPositionParams position) {
    return TextDocumentService.super.implementation(position);
  }

  @JsonRequest
  public CompletableFuture<List<ColorInformation>> documentColor(DocumentColorParams params) {
    return TextDocumentService.super.documentColor(params);
  }

  @JsonRequest
  public CompletableFuture<List<ColorPresentation>> colorPresentation(ColorPresentationParams params) {
    return TextDocumentService.super.colorPresentation(params);
  }

  private void setClient(LanguageClient client) {
    this.client = client;
  }

  private boolean willSaveWaitUntilCalled() {
    return this.willSaveWaitUntilCalled;
  }

  private void setWillSaveWaitUntilCalled(boolean willSaveWaitUntilCalled) {
    this.willSaveWaitUntilCalled = willSaveWaitUntilCalled;
  }

  public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
    SymbolInformation[] symbols = new SymbolInformation[]{
      new SymbolInformation("SymbA", SymbolKind.Class, new Location(this.uris.get(0), this.ranges[4])),
      new SymbolInformation("SymbB", SymbolKind.Enum, new Location(this.uris.get(0), this.ranges[2]))
    };
    return params.getQuery().isEmpty()
      ? CompletableFuture.completedFuture(Arrays.asList(symbols))
      : CompletableFuture.completedFuture(Collections.singletonList(symbols[0]));
  }

  public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
    this.client.logMessage(new MessageParams(MessageType.Info, "DidChangeWatchedFiles " + params));
  }

  public void didChangeConfiguration(DidChangeConfigurationParams params) {
    this.client.logMessage(new MessageParams(MessageType.Info, "didChangeConfiguration " + params));
  }

  public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
    String uri = params.getTextDocument().getUri();
    return uri.contains("test1")
      ? CompletableFuture.completedFuture(Arrays.asList(new Location(uri, ranges[1]), new Location(uri, ranges[4])))
      : CompletableFuture.completedFuture(Collections.singletonList(new Location(uris.get(uris.size() - 1), ranges[3])));
  }

  public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
    return CompletableFuture.completedFuture(unresolved);
  }

  public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
    return CompletableFuture.completedFuture(Collections.singletonList(new CodeLens(ranges[0], new Command(this.commands[0], this.commands[0]), null)));
  }

  public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
    return CompletableFuture.completedFuture(Arrays.asList(new DocumentHighlight(this.ranges[1], DocumentHighlightKind.Read),
      new DocumentHighlight(this.ranges[4], DocumentHighlightKind.Write),
      new DocumentHighlight(this.ranges[2], DocumentHighlightKind.Text)));
  }

  public void didChange(DidChangeTextDocumentParams params) {
    this.client.publishDiagnostics(new PublishDiagnosticsParams(this.uris.get(0), Arrays.asList(
      new Diagnostic(new Range(new Position(0, 0), new Position(0, 20)), "Warning", DiagnosticSeverity.Warning, "TestServer", "code0"),
      new Diagnostic(this.ranges[3], "Error", DiagnosticSeverity.Error, "Also TestServer"),
      new Diagnostic(this.ranges[4], "Info", DiagnosticSeverity.Information, null, "code1"),
      new Diagnostic(this.ranges[5], "Hint", DiagnosticSeverity.Hint, null, null)))
    );
  }

  public CompletableFuture<Hover> hover(TextDocumentPositionParams position) {
    String uri = position.getTextDocument().getUri();
    return uri.contains("test1")
      ? CompletableFuture.completedFuture(new Hover(Collections.singletonList(Either.forRight(new MarkedString(null, "**Bold** *Italic*")))))
      : CompletableFuture.completedFuture(new Hover(Collections.singletonList(Either.forLeft("This is hover"))));
  }

  public CompletableFuture<List<? extends SymbolInformation>> documentSymbol(DocumentSymbolParams params) {
    CompletableFuture<List<? extends SymbolInformation>> completableFuture;
    String uri = params.getTextDocument().getUri();
    if (uri.contains("test1")) {
      SymbolInformation[] symbols = new SymbolInformation[]{
        new SymbolInformation("SymbA", SymbolKind.Class, new Location(uri, this.ranges[1])),
        new SymbolInformation("SymbB", SymbolKind.Enum, new Location(uri, this.ranges[4]))
      };
      completableFuture = CompletableFuture.completedFuture(Arrays.asList(symbols));
    } else {
      String nUri = this.uris.get(this.uris.size() - 1);
      SymbolInformation[] symbols = new SymbolInformation[]{
        new SymbolInformation("SymbA", SymbolKind.Class, new Location(nUri, this.ranges[1])),
        new SymbolInformation("SymbB", SymbolKind.Enum, new Location(nUri, this.ranges[4]))
      };
      completableFuture = CompletableFuture.completedFuture(Arrays.asList(symbols));
    }
    return completableFuture;
  }

  public void didClose(DidCloseTextDocumentParams params) {
    this.client.logMessage(new MessageParams(MessageType.Info, "DidClose"));
  }

  public void didSave(DidSaveTextDocumentParams params) {
    this.client.logMessage(new MessageParams(MessageType.Info, "DidSave"));
  }

  public CompletableFuture<List<? extends Location>> definition(TextDocumentPositionParams position) {
    String uri = position.getTextDocument().getUri();
    return uri.contains("test1")
      ? CompletableFuture.completedFuture(Collections.singletonList(new Location(uri, this.ranges[1])))
      : CompletableFuture.completedFuture(Collections.singletonList(new Location(this.uris.get(this.uris.size() - 1), this.ranges[4])));
  }

  public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
    return CompletableFuture.completedFuture(unresolved);
  }

  public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams position) {
    return CompletableFuture.completedFuture(Either.forRight(new CompletionList(Arrays.asList(completionItems))));
  }

  public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
    return CompletableFuture.completedFuture(Collections.singletonList(new TextEdit(this.range0, "Formatted")));
  }

  public void didOpen(DidOpenTextDocumentParams params) {
  }

  public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
    String uri = position.getTextDocument().getUri();
    if (uri.contains("test1"))
      return CompletableFuture.completedFuture(new SignatureHelp(
          Arrays.asList(
            new SignatureInformation("SigA(paramA : String, paramB : Int = 2)", "Do A", Arrays.asList(
              new ParameterInformation("paramA", "paramA000"),
              new ParameterInformation("paramB", "paramB001"))
            ),
            new SignatureInformation("SigB(paramA : String)", "Do B", Collections.singletonList(
              new ParameterInformation("paramA", "paramA010"))
            )
          ),
          0,
          1
        )
      );
    else
      return CompletableFuture.completedFuture(new SignatureHelp(
          Arrays.asList(
            new SignatureInformation(
              "SigAA(paramAA : String, paramBB : String = \"\")",
              "Does AA",
              Arrays.asList(
                new ParameterInformation("paramAA", "paramAA100"),
                new ParameterInformation("paramBB", "paramBB101")
              )
            ),
            new SignatureInformation(
              "SigBB(paramBB : Int)",
              "Does BB",
              Collections.singletonList(
                new ParameterInformation("paramBB", "paramBB110")
              )
            )
          ),
          1,
          0
        )
      );
  }

  public CompletableFuture<List<DocumentLink>> documentLink(DocumentLinkParams params) {
    return TextDocumentService.super.documentLink(params);
  }

  public CompletableFuture<DocumentLink> documentLinkResolve(DocumentLink params) {
    return TextDocumentService.super.documentLinkResolve(params);
  }

  public void willSave(WillSaveTextDocumentParams params) {
    TextDocumentService.super.willSave(params);
  }

  public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
    CompletableFuture<Object> completableFuture;
    String title = params.getCommand();
    if (Objects.equals(title, this.commands[0])) {
      Map<String, List<TextEdit>> changes = new HashMap<>();
      changes.put(
        uris.get(0),
        Collections.singletonList(
          new TextEdit(range0, "Command0")
        )
      );
      this.client.applyEdit(new ApplyWorkspaceEditParams(new WorkspaceEdit(changes)));
      completableFuture = CompletableFuture.completedFuture(new Object());
    } else {
      Map<String, List<TextEdit>> changes = new HashMap<>();
      changes.put(
        uris.get(0),
        Collections.singletonList(
          new TextEdit(range0, "Command1or2")
        )
      );
      completableFuture = CompletableFuture.completedFuture(new WorkspaceEdit(changes));
    }
    return completableFuture;
  }

  public CompletableFuture<List<TextEdit>> willSaveWaitUntil(WillSaveTextDocumentParams params) {
    CompletableFuture<List<TextEdit>> completableFuture;
    if (!this.willSaveWaitUntilCalled()) {
      this.setWillSaveWaitUntilCalled(true);
      completableFuture = CompletableFuture.completedFuture(Collections.singletonList((new TextEdit(this.range0, "WillSaveWaitUntil"))));
    } else {
      completableFuture = CompletableFuture.completedFuture(Collections.emptyList());
    }
    return completableFuture;
  }

  public void initialized() {
    LanguageServer.super.initialized();
  }

  public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
    Range range = params.getRange();
    int length = range.getEnd().getCharacter() - range.getStart().getCharacter();
    return CompletableFuture.completedFuture(Collections.singletonList(new TextEdit(range, StringUtils.repeat("f", length))));
  }

  public CompletableFuture<List<? extends Command>> codeAction(CodeActionParams params) {
    if (params.getRange().getEnd().getCharacter() < 25 && params.getRange().getEnd().getLine() < 1)
      return CompletableFuture.completedFuture(Collections.singletonList(new Command(this.commands[0], this.commands[0])));
    else
      return CompletableFuture.completedFuture(Arrays.asList(new Command(this.commands[1], this.commands[1]), new Command(this.commands[2], this.commands[2])));
  }

  public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
    CompletableFuture<MessageActionItem> req = this.client.showMessageRequest(new ShowMessageRequestParams(Arrays.asList(
      new MessageActionItem("One"),
      new MessageActionItem("Two"),
      new MessageActionItem("Three")))
    );
    req.thenAccept(item -> this.client.showMessage(new MessageParams(MessageType.Info, "Chose " + item.getTitle())));
    Map<String, List<TextEdit>> changes = new HashMap<>();
    changes.put(params.getTextDocument().getUri(), Collections.singletonList(new TextEdit(ranges[0], "Renamed")));
    return CompletableFuture.completedFuture(new WorkspaceEdit(changes));
  }

  public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
    String uri = params.getTextDocument().getUri();
    if (uri.contains("test1"))
      return CompletableFuture.completedFuture(Collections.singletonList(new TextEdit(this.range0, "Formatted")));
    else
      return CompletableFuture.completedFuture(Collections.singletonList(new TextEdit(this.ranges[1], "")));
  }

  public TextDocumentService getTextDocumentService() {
    return this;
  }

  public void exit() {
    System.exit(0);
  }

  public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
    return CompletableFuture.completedFuture(new InitializeResult(this.capabilities));
  }

  public void connect(LanguageClient client) {
    this.setClient(client);
  }

  public WorkspaceService getWorkspaceService() {
    return this;
  }

  public CompletableFuture<Object> shutdown() {
    return CompletableFuture.completedFuture(new Object());
  }

  public void initialized(InitializedParams params) {
  }

  public TestServer() {
    CompletionItem[] completionItems = new CompletionItem[4];
    CompletionItem a = new CompletionItem("A");
    a.setInsertText("CompletionA");
    a.setInsertTextFormat(InsertTextFormat.PlainText);
    a.setKind(CompletionItemKind.Enum);
    a.setDetail("DetailA");
    a.setDocumentation("DocumentationA");
    completionItems[0] = a;
    CompletionItem b = new CompletionItem("B");
    b.setTextEdit(new TextEdit(this.range0, "CompletedB"));
    b.setAdditionalTextEdits(Collections.singletonList(new TextEdit(new Range(new Position(0, 20), new Position(0, 30)), "CompletedB")));
    b.setKind(CompletionItemKind.Class);
    b.setDetail("DetailB");
    completionItems[1] = b;
    CompletionItem c = new CompletionItem("C");
    c.setCommand(new Command(this.commands[0], this.commands[0]));
    c.setDocumentation("DocumentationC");
    completionItems[2] = c;
    CompletionItem d = new CompletionItem("D");
    d.setKind(CompletionItemKind.Method);
    completionItems[3] = d;

    this.completionItems = completionItems;
    this.uris = Arrays.asList("temp:///src/test1.test", "temp:///src/test1.test");
    this.capabilities = new ServerCapabilities();
    this.syncKindOptions = new TextDocumentSyncOptions();
    this.positions = Arrays.asList(this.pos0, new Position(0, 10), new Position(0, 20), new Position(0, 30), new Position(1, 0), new Position(1, 10), new Position(1, 20), new Position(1, 30)).toArray(new Position[]{});
    this.capabilities.setCodeActionProvider(true);
    this.capabilities.setCodeLensProvider(new CodeLensOptions(true));
    this.capabilities.setCompletionProvider(new CompletionOptions(true, Collections.singletonList("^")));
    this.capabilities.setDefinitionProvider(true);
    this.capabilities.setDocumentFormattingProvider(true);
    this.capabilities.setDocumentHighlightProvider(true);
    this.capabilities.setDocumentLinkProvider(new DocumentLinkOptions(true));
    this.capabilities.setDocumentOnTypeFormattingProvider(new DocumentOnTypeFormattingOptions("\u00b0", Collections.singletonList("\u00a7"))); // "°", "§"
    this.capabilities.setDocumentRangeFormattingProvider(true);
    this.capabilities.setDocumentSymbolProvider(true);
    this.capabilities.setExecuteCommandProvider(new ExecuteCommandOptions(Arrays.asList(this.commands)));
    this.capabilities.setHoverProvider(true);
    this.capabilities.setReferencesProvider(true);
    this.capabilities.setRenameProvider(true);
    this.capabilities.setSignatureHelpProvider(new SignatureHelpOptions(Collections.singletonList("(")));
    this.ranges = Arrays.stream(this.positions).map(pos -> new Range(pos, pos)).collect(Collectors.toList()).toArray(new Range[]{});
    this.syncKindOptions.setChange(TextDocumentSyncKind.Incremental);
    this.syncKindOptions.setOpenClose(true);
    this.syncKindOptions.setSave(new SaveOptions(true));
    this.syncKindOptions.setWillSave(true);
    this.syncKindOptions.setWillSaveWaitUntil(true);
    this.capabilities.setTextDocumentSync(this.syncKindOptions);
    this.capabilities.setWorkspaceSymbolProvider(true);
    this.setWillSaveWaitUntilCalled(false);
  }

  public static void main(String[] args) {
    InputStream in = System.in;
    PrintStream out = System.out;
    TestServer server = new TestServer();
    Launcher launcher = LSPLauncher.createServerLauncher(server, in, out);
    LanguageClient client = (LanguageClient) launcher.getRemoteProxy();
    server.connect(client);
    launcher.startListening();
    System.err.println("Listening");
  }
}
