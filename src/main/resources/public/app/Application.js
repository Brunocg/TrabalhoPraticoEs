Ext.define('TrabalhoPraticoEsApp.Application', {
    extend: 'Ext.app.Application',

    name: 'TrabalhoPraticoEsApp',

    stores: [
        // TODO: add global / shared stores here
    ],
    views: [
        'TrabalhoPraticoEsApp.view.login.Login',
        'TrabalhoPraticoEsApp.view.main.Main'
    ],
    launch: function () {
        var supportsLocalStorage = Ext.supports.LocalStorage, 
            loggedIn;

        if (!supportsLocalStorage) {
            Ext.Msg.alert('Seu navegador não suporta armazenamento local');
            return;
        }

        loggedIn = localStorage.getItem("TutorialLoggedIn");

        Ext.widget(loggedIn ? 'app-main' : 'login');

    }
});