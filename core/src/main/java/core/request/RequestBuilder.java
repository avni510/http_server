package core.request;

public class RequestBuilder {
  private Enum<RequestMethod> requestMethod;
  private String uri;
  private String httpVersion;
  private String header = null;
  private String body = null;

  public RequestBuilder() {}

  public RequestBuilder setRequestMethod(Enum<RequestMethod> requestMethod) {
    this.requestMethod = requestMethod;
    return this;
  }

  public RequestBuilder setUri(String uri) {
    this.uri = uri;
    return this;
  }

  public RequestBuilder setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
    return this;
  }

  public RequestBuilder setHeader(String header) {
    this.header = header;
    return this;
  }

  public RequestBuilder setBody(String body) {
    this.body = body;
    return this;
  }

  public Request build() {
    return new Request(requestMethod, uri, httpVersion, header, body);
  }
}
