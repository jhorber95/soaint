import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DetalleVentaComponentsPage,
  /* DetalleVentaDeleteDialog, */
  DetalleVentaUpdatePage,
} from './detalle-venta.page-object';

const expect = chai.expect;

describe('DetalleVenta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let detalleVentaComponentsPage: DetalleVentaComponentsPage;
  let detalleVentaUpdatePage: DetalleVentaUpdatePage;
  /* let detalleVentaDeleteDialog: DetalleVentaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DetalleVentas', async () => {
    await navBarPage.goToEntity('detalle-venta');
    detalleVentaComponentsPage = new DetalleVentaComponentsPage();
    await browser.wait(ec.visibilityOf(detalleVentaComponentsPage.title), 5000);
    expect(await detalleVentaComponentsPage.getTitle()).to.eq('soaintApp.detalleVenta.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(detalleVentaComponentsPage.entities), ec.visibilityOf(detalleVentaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DetalleVenta page', async () => {
    await detalleVentaComponentsPage.clickOnCreateButton();
    detalleVentaUpdatePage = new DetalleVentaUpdatePage();
    expect(await detalleVentaUpdatePage.getPageTitle()).to.eq('soaintApp.detalleVenta.home.createOrEditLabel');
    await detalleVentaUpdatePage.cancel();
  });

  /* it('should create and save DetalleVentas', async () => {
        const nbButtonsBeforeCreate = await detalleVentaComponentsPage.countDeleteButtons();

        await detalleVentaComponentsPage.clickOnCreateButton();

        await promise.all([
            detalleVentaUpdatePage.ventaSelectLastOption(),
            detalleVentaUpdatePage.pruductoSelectLastOption(),
        ]);


        await detalleVentaUpdatePage.save();
        expect(await detalleVentaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await detalleVentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last DetalleVenta', async () => {
        const nbButtonsBeforeDelete = await detalleVentaComponentsPage.countDeleteButtons();
        await detalleVentaComponentsPage.clickOnLastDeleteButton();

        detalleVentaDeleteDialog = new DetalleVentaDeleteDialog();
        expect(await detalleVentaDeleteDialog.getDialogTitle())
            .to.eq('soaintApp.detalleVenta.delete.question');
        await detalleVentaDeleteDialog.clickOnConfirmButton();

        expect(await detalleVentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
