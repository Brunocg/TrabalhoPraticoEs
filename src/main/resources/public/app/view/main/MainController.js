Ext.define('TrabalhoPraticoEsApp.view.main.MainController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.main',

    onClickButton: function () {
        localStorage.removeItem('TutorialLoggedIn');

        this.getView().destroy();

        Ext.widget('login');
    }
});