<html>
  <head>
    <title>.: Avatar Plugin :.</title>
  </head>
  <body>
    <g:form name="obtainGravatar" url="[controller:'avatar',action:'obtain']" update="avatar">
      e-m@il: <g:textField name="email" />
      <input type="submit" name="submit" value="Show me...">
    </g:form>
    <avatar:gravatar email="${email ?: 'user@user.com'}" size="50"/>
    <g:form name="obtainTwitter" url="[controller:'avatar',action:'obtain']" update="avatar">
      Twitter: <g:textField name="twitter" />
      <input type="submit" name="submit" value="Show me...">
    </g:form>
        <avatar:twitter user="${twitter ?: 'twitter'}" size="50"/>
  </body>
</html>