Ext.define('TrabalhoPraticoEsApp.view.login.LoginViewModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.login',  

    statics: {
       efetuarLogin: function(login, senha, success, failure, scope){
            var data = {
                "login": login,
                "senha": senha
            };

            Ext.Ajax.request({
                url: 'pessoa/usuario/login',
                method: 'POST',
                timeout: 120000,
                jsonData: Ext.encode(data),
                success: success,
                failure: failure,
                scope: scope
            });
        }
   }
});