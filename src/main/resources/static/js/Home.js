function renderHome() {
    let text = "<center>";
    text += "<h1>Home</h1>";
    text += "<h2>Sub Titles</h2>";
    text += "</center>";
    return text;
}

//Uses contentDiv as input param!
function renderHomePage() {
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == XMLHttpRequest.DONE && this.status == 200) {
        contentDiv.innerHTML = this.responseText;
      }
    };

    const requestUrl = fixRequestUrlPrefix() + "pages/Home.html";
    //console.log("renderHomePage.requestUrl:" + requestUrl);

    xmlhttp.open("GET", requestUrl, true);

    //xmlhttp.setRequestHeader("Content-Type", "text/html");
    xmlhttp.setRequestHeader("Accept", "text/html");
    
    xmlhttp.send();
}
