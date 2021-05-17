function renderUserAdd(contentDiv) {
    if (typeof contentDiv=="undefined") {
      alert("contentDiv is undefined!");
      return;
    }

    //-----------------
    //const user = {}; //Empty row!

    var text = [];
    text.push('<h1>User Add</h1>');

    text.push('<br/>');
    text.push('<form>');
    text.push('<table>');

    text.push('<tr>');
    text.push('<th align="right">Id (*)</th>');
    text.push('<td>[Automatic]<td/>');
    text.push('</tr>');

    text.push('<tr>');
    text.push('<th align="right">Code (*)</th>');
    text.push('<td><input type="text" name="code" value="" required/><td/>');
    text.push('</tr>');

    text.push('<tr>');
    text.push('<th align="right">Name (*)</th>');
    text.push('<td><input type="text" name="name" value="" required/><td/>');
    text.push('</tr>');

    text.push('<tr>');
    text.push('<th align="right">Surname (*)</th>');
    text.push('<td><input type="text" name="surname" value="" required/><td/>');
    text.push('</tr>');

    text.push('<tr>');
    text.push('<th align="right">Password (*)</th>');
    text.push('<td><input type="password" name="password" value="" required/><td/>');
    text.push('</tr>');

    text.push('<tr>');
    text.push('<th align="right">Phone</th>');
    text.push('<td><input type="text" name="phone" value=""/><td/>');
    text.push('</tr>');

    text.push('<tr>');
    text.push('<th align="right">Status (*)</th>');

    text.push('<td>');

    text.push('<select name="status">');
    text.push(' <option value="1">Active</option>');
    text.push(' <option value="0">Passive</option>');
    text.push('</select>');

    text.push('</td>');
    text.push('</tr>');

    text.push('</table>');

    text.push('<br/><input type="button" onclick="saveUserAdd(contentDiv, this.form)" value="Save"/>');
    text.push('&nbsp;');
    text.push('<button type="button" onclick="renderUserList(contentDiv)">Cancel</button>');
    text.push('</form>');

    contentDiv.innerHTML = text.join("");
}


function saveUserAdd(contentDiv, frm) {
  const frmElements = frm.elements;
  const elementCount = frmElements.length;

  const user = {}; //Empty row!

  for(let i = 0; i < elementCount; i++) {
    let inObj = frmElements[i];
    let inObjName = inObj.name;
    if (inObjName==null || inObjName=="")
      continue;

    let isValid = inObj.checkValidity();

    if (isValid == false) {
        alert(inObjName + ", error:" + inObj.validationMessage);
        return;
    }

    user[inObjName] = inObj.value;
  }//for

  //---------------------
  const xmlhttp = new XMLHttpRequest();

  xmlhttp.onreadystatechange = function() {
    if (this.readyState == XMLHttpRequest.DONE) {
      if (this.status == 201) {
    	  alert("Addition is successful");
        renderUserList(contentDiv);
      }
      else {
        alert("Add Result Error:" + this.responseText);
      }
    }
  };


  //---------------------
  const requestUrl = fixRequestUrlPrefix() + "api/user/add";

  xmlhttp.open("POST", requestUrl, true);

  //Note: HTTP headers can be used to describe the content being sent or requested within an HTTP request.
  xmlhttp.setRequestHeader("Content-Type", "application/json");
  xmlhttp.setRequestHeader("Accept", "application/json");

  const userAddStr = JSON.stringify(user);
  xmlhttp.send(userAddStr);
}//save_UserAdd
