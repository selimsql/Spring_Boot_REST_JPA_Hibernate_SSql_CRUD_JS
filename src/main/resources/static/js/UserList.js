function renderUserList(contentDiv) {
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == XMLHttpRequest.DONE) {
        var respHtml;
        if (this.status == 200) {
          const listUser = JSON.parse(this.responseText);
          respHtml = prepareUserList(listUser);
        }
        else {
          //const error = JSON.parse(this.responseText);
          const error = this.responseText;
          respHtml = "Status:" + this.status + "<br>Error:" + error;
        }

        contentDiv.innerHTML = respHtml;
      }//if XMLHttpRequest.DONE
    };

    const requestUrl = fixRequestUrlPrefix() + "api/user/userList";
    //console.log("UserList.requestUrl:" + requestUrl);

    xmlhttp.open("GET", requestUrl, true);

    //Note: HTTP headers can be used to describe the content being sent or requested within an HTTP request.
    //xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.setRequestHeader("Accept", "application/json");

    xmlhttp.send();
}


function prepareUserList(listUser) {
  //Used to build HTML in a optimized way.
  var text = [];
  text.push('<h1>User List</h1>');
  text.push('<input type="button" onclick="renderUserAdd(contentDiv)" value="Add User"/>');
  //text += '<br/><table style="border:1px solid black; padding=3px;">';
  text.push('<br/>');
  //text.push('<table class="table table-bordered table-hover">');
  text.push('<table class="tableHover">');
  text.push('<thead><tr>');
  text.push(' <th>Id</th> <th>Code</th> <th>Name</th> <th>Surname</th> <th>Status</th> <th>Operations</th>');
  text.push('</tr></thead>');

  text.push('<tbody>');
  for(keyItem in listUser) {
      let user = listUser[keyItem];
      let userId = user.id;
      //text.push('<tr style="border:1px solid blue; padding=3px;">');
      text.push('<tr>');
      text.push('<td>' + userId + '</td>');
      text.push('<td>' + user.code + '</td>');
      text.push('<td>' + user.name + '</td>');
      text.push('<td>' + user.surname + '</td>');
      text.push('<td>' + user.status + '</td>');
      text.push('<td>');
      text.push('&nbsp;<input type="button" onclick="renderUserUpdate(contentDiv, ' + userId + ')" value="Update"/>');
      text.push('&nbsp;<input type="button" onclick="renderUserDelete(contentDiv, ' + userId + ')" value="Delete"/>');
      text.push('&nbsp;<input type="button" onclick="renderUserView(contentDiv, ' + userId + ')" value="View"/>');
      text.push('</td>');
      text.push('</tr>');
  }

  text.push('</tbody>');
  text.push('</table>');

  return text.join("");
}//prepareUserListNEW
