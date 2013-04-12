<html>
<head>
    <title>Grails Avatar Plugin</title>
</head>

<body>
<g:form name="obtainGravatar" mapping="index" method="POST">

    e-mail: <g:textField name="email" value="${email ?: 'user@user.com'}"/>
    <avatar:gravatar secure="true" email="${email ?: 'user@user.com'}" size="50"/>
    <br/>

    Twitter: <g:textField name="twitter" value="${twitter ?: 'twitter'}"/>
    <avatar:twitter user="${twitter ?: 'twitter'}" size="50"/>
    <br/>

    Facebook: <g:textField name="facebook" value="${facebook ?: 'facebook'}"/>
    <avatar:facebook user="${facebook ?: 'facebook'}" size="50"/>
    <br/>

    <input type="submit" name="submit" value="Get avatars">
</g:form>

</body>
</html>