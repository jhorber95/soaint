import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  VentaComponentsPage,
  /* VentaDeleteDialog, */
  VentaUpdatePage,
} from './venta.page-object';

const expect = chai.expect;

describe('Venta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ventaComponentsPage: VentaComponentsPage;
  let ventaUpdatePage: VentaUpdatePage;
  /* let ventaDeleteDialog: VentaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Ventas', async () => {
    await navBarPage.goToEntity('venta');
    ventaComponentsPage = new VentaComponentsPage();
    await browser.wait(ec.visibilityOf(ventaComponentsPage.title), 5000);
    expect(await ventaComponentsPage.getTitle()).to.eq('soaintApp.venta.home.title');
    await browser.wait(ec.or(ec.visibilityOf(ventaComponentsPage.entities), ec.visibilityOf(ventaComponentsPage.noResult)), 1000);
  });

  it('should load create Venta page', async () => {
    await ventaComponentsPage.clickOnCreateButton();
    ventaUpdatePage = new VentaUpdatePage();
    expect(await ventaUpdatePage.getPageTitle()).to.eq('soaintApp.venta.home.createOrEditLabel');
    await ventaUpdatePage.cancel();
  });

  /* it('should create and save Ventas', async () => {
        const nbButtonsBeforeCreate = await ventaComponentsPage.countDeleteButtons();

        await ventaComponentsPage.clickOnCreateButton();

        await promise.all([
            ventaUpdatePage.setFechaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            ventaUpdatePage.clienteSelectLastOption(),
        ]);

        expect(await ventaUpdatePage.getFechaInput()).to.contain('2001-01-01T02:30', 'Expected fecha value to be equals to 2000-12-31');

        await ventaUpdatePage.save();
        expect(await ventaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await ventaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Venta', async () => {
        const nbButtonsBeforeDelete = await ventaComponentsPage.countDeleteButtons();
        await ventaComponentsPage.clickOnLastDeleteButton();

        ventaDeleteDialog = new VentaDeleteDialog();
        expect(await ventaDeleteDialog.getDialogTitle())
            .to.eq('soaintApp.venta.delete.question');
        await ventaDeleteDialog.clickOnConfirmButton();

        expect(await ventaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
