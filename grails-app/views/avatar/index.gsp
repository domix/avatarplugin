<html>
  <head>
    <title>Grails Avatar Plugin</title>
  </head>
  <body>
    <g:form name="obtainGravatar" url="[controller:'avatar',action:'obtain']" update="avatar">
      e-mail: <g:textField name="email" />
    
    <avatar:gravatar secure = "true" email="${email ?: 'user@user.com'}" size="50"/>
    <br/>
      Twitter: <g:textField name="twitter" />
    
        <avatar:twitter user="${twitter ?: 'twitter'}" size="50"/>
        <br/>
    
          Facebook: <g:textField name="facebook" />
          
<avatar:facebook user="${facebook ?: 'facebook'}" size="50"/>
        <br/>
<input type="submit" name="submit" value="Get avatars">
        </g:form>
        

  
  </body>
</html>