var contentDiv = null; //Global param.
var requestUrlPrefix = null; //Global param.

function fixContentDiv() {
    contentDiv = document.getElementById("content");
}

function fixRequestUrlPrefix() {
    if (requestUrlPrefix!=null)
        return requestUrlPrefix;

    const locProtocol = window.location.protocol; //"file:", "http:"
    if (locProtocol.startsWith("file"))
        requestUrlPrefix = "http://localhost:8080/";
    else
        requestUrlPrefix = "";

    return requestUrlPrefix;
}

function updateContent(execFunc) {
    contentDiv.innerHTML = execFunc();
}

function updateContentAfterExecFunc(execFunc) {
    execFunc(contentDiv);
}
