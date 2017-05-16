package http_server;

public class Support {

  public Request createRequest(Enum<RequestMethod> requestMethod, String uri, String httpVersion, String headers, String body){
    Request request = new RequestBuilder()
        .setRequestMethod(requestMethod)
        .setUri(uri)
        .setHttpVersion(httpVersion)
        .setHeader(headers)
        .setBody(body)
        .build();
    return request;
  }

  public Request createRequest(Enum<RequestMethod> requestMethod, String uri, String httpVersion, String headers){
    Request request = new RequestBuilder()
        .setRequestMethod(requestMethod)
        .setUri(uri)
        .setHttpVersion(httpVersion)
        .setHeader(headers)
        .build();
    return request;
  }

  public Request createRequest(Enum<RequestMethod> requestMethod, String uri, String httpVersion){
    Request request = new RequestBuilder()
        .setRequestMethod(requestMethod)
        .setUri(uri)
        .setHttpVersion(httpVersion)
        .build();
    return request;
  }
}
