Ext.define('TrabalhoPraticoEsApp.view.user.UserViewModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.user',  

    statics: {
       efetuarLogin:function(login, senha, success, failure, callback, scope){
            var data = {
                "login": login,
                "senha": senha
            };

            Ext.Ajax.request({
                url:'usuario/login',
                method:'POST',
                timeout: 120000,
                jsonData:Ext.encode([data]),
                success:success,
                failure:failure,
                callback:callback,
                scope:scope
            });
        }
   }
});