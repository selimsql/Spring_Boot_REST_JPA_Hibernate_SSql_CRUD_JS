var userLast = null; //Global param:user
var operation = null; //Global param: "Update", "Delete", "View"

function renderUserUpdate(contentDiv, userId) {
  operation = "Update";
  return renderUserEdit(contentDiv, userId);
}

function renderUserDelete(contentDiv, userId) {
  operation = "Delete";
  return renderUserEdit(contentDiv, userId);
}

function renderUserView(contentDiv, userId) {
  operation = "View";
  return renderUserEdit(contentDiv, userId);
}

function renderUserEdit(contentDiv, userId) {
    if (typeof contentDiv=="undefined") {
      alert("contentDiv is undefined!");
      return;
    }

    if (typeof userId=="undefined") {
      alert("userId is undefined!");
      return;
    }

    //-----------------
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == XMLHttpRequest.DONE) {
        var respHtml;
        if (this.status == 200) {
          userLast = JSON.parse(this.responseText);
          respHtml = prepareUserEdit(userLast);
        }
        else {
          //const error = JSON.parse(this.responseText);
          const error = this.responseText;
          respHtml = "Status:" + this.status + "<br>Error:" + error;
        }

        contentDiv.innerHTML = respHtml;
      }//if XMLHttpRequest.DONE
    };

    //------------------------------
    const requestUrl = fixRequestUrlPrefix() + "api/user/userRow/" + userId;

    xmlhttp.open("GET", requestUrl, true);

    //Note: HTTP headers can be used to describe the content being sent or requested within an HTTP request.
    //xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.setRequestHeader("Accept", "application/json");

    xmlhttp.send();
}//render_UserEdit


function prepareUserEdit(user) {
  var text = [];
  text.push('<h1>User ' + operation + '</h1>');
  text.push('<br/>');
  text.push('<form>');
  text.push('<table>');

  text.push('<tr>');
  text.push('<th align="right">Id (*)</th>');
  text.push('<td>' + user.id + '<td/>');
  text.push('</tr>');

  text.push('<tr>');
  text.push('<th align="right">Code (*)</th>');
  text.push('<td><input type="text" name="code" value="' + user.code + '" required/><td/>');
  text.push('</tr>');

  text.push('<tr>');
  text.push('<th align="right">Name (*)</th>');
  text.push('<td><input type="text" name="name" value="' + user.name + '" required/><td/>');
  text.push('</tr>');

  text.push('<tr>');
  text.push('<th align="right">Surname (*)</th>');
  text.push('<td><input type="text" name="surname" value="' + user.surname + '" required/><td/>');
  text.push('</tr>');

  text.push('<tr>');
  text.push('<th align="right">Password (*)</th>');
  text.push('<td><input type="password" name="password" value="' + user.password + '" required/><td/>');
  text.push('</tr>');

  let userPhone = user.phone;
  if (userPhone==null)
    userPhone = "";

  text.push('<tr>');
  text.push('<th align="right">Phone</th>');
  text.push('<td><input type="text" name="phone" value="' + userPhone + '"/><td/>');
  text.push('</tr>');

  text.push('<tr>');
  text.push('<th align="right">Status (*)</th>');

  text.push('<td>');

  text.push('<select name="status">');

  const statusArray = [{key:1, value:"ACTIVE"}, {key:0, value:"PASSIVE"}];

  let userStatus = user.status;
  for(let i = 0; i < statusArray.length; i++) {
    let statusObj = statusArray[i];
    let statusKey = statusObj.key;
    let strOption = "<option value='" + statusKey + "'";

    if (statusKey == userStatus)
      strOption += " selected";

    strOption += ">" + statusObj.value + "</option>";

    text.push(strOption);
  }//for-i

  text.push('</select>');

  text.push('</td>');
  text.push('</tr>');

  text.push('</table>');

  text.push('<br/>');
  let backTitle = "Cancel";
  if (operation=="Update" || operation=="Delete") {
    text.push('<input type="button" onclick="saveUser' + operation + '(contentDiv, this.form, userLast)" value="' + operation + '"/>');
    text.push('&nbsp;');
  }
  else {
    backTitle = "OK";
  }

  text.push('<button type="button" onclick="renderUserList(contentDiv)">' + backTitle + '</button>');
  text.push('</form>');

  return text.join("");
}//prepare_UserEdit


function saveUserUpdate(contentDiv, frm, user) {
  const frmElements = frm.elements;
  const elementCount = frmElements.length;

  for(let i = 0; i < elementCount; i++) {
    var inObj = frmElements[i];
    var inObjName = inObj.name;
    if (inObjName==null)
      continue;

    if (inObj.checkValidity() == false) {
        alert(inObjName + ", error:" + inObj.validationMessage);
        return;
    }

    user[inObjName] = inObj.value;
  }//for

  const userNewStr = JSON.stringify(user);

  //---------------------
  const xmlhttp = new XMLHttpRequest();

  xmlhttp.onreadystatechange = function() {
    if (this.readyState == XMLHttpRequest.DONE) {
      if (this.status == 200) {
    	  alert("Update is successful");
    	  renderUserList(contentDiv);
      }
      else {
    	  alert("Update Result Error:" + this.responseText);
      }
    }
  };


  //---------------------
  const requestUrl = fixRequestUrlPrefix() + "api/user/update/" + user.id;

  xmlhttp.open("PUT", requestUrl, true);

  //Note: HTTP headers can be used to describe the content being sent or requested within an HTTP request.
  xmlhttp.setRequestHeader("Content-Type", "application/json");
  xmlhttp.setRequestHeader("Accept", "application/json");

  xmlhttp.send(userNewStr);
}//save_UserUpdate


function saveUserDelete(contentDiv, frm, user) {
  const userId = user.id;

  const xmlhttp = new XMLHttpRequest();

  xmlhttp.onreadystatechange = function() {
    if (this.readyState == XMLHttpRequest.DONE) {
      if (this.status == 200) {
        alert("Delete is successful");
        renderUserList(contentDiv);
      }
      else {
        alert("Delete Result Error:" + this.responseText);
      }
    }
  };


  //---------------------
  const requestUrl = fixRequestUrlPrefix() + "api/user/delete/" + userId;

  xmlhttp.open("DELETE", requestUrl, true);

  xmlhttp.setRequestHeader("Content-Type", "application/json");
  xmlhttp.setRequestHeader("Accept", "application/json");

  xmlhttp.send("userId=" + userId);
}//save_UserDelete
