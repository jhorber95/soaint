import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductoComponentsPage, ProductoDeleteDialog, ProductoUpdatePage } from './producto.page-object';

const expect = chai.expect;

describe('Producto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productoComponentsPage: ProductoComponentsPage;
  let productoUpdatePage: ProductoUpdatePage;
  let productoDeleteDialog: ProductoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Productos', async () => {
    await navBarPage.goToEntity('producto');
    productoComponentsPage = new ProductoComponentsPage();
    await browser.wait(ec.visibilityOf(productoComponentsPage.title), 5000);
    expect(await productoComponentsPage.getTitle()).to.eq('soaintApp.producto.home.title');
    await browser.wait(ec.or(ec.visibilityOf(productoComponentsPage.entities), ec.visibilityOf(productoComponentsPage.noResult)), 1000);
  });

  it('should load create Producto page', async () => {
    await productoComponentsPage.clickOnCreateButton();
    productoUpdatePage = new ProductoUpdatePage();
    expect(await productoUpdatePage.getPageTitle()).to.eq('soaintApp.producto.home.createOrEditLabel');
    await productoUpdatePage.cancel();
  });

  it('should create and save Productos', async () => {
    const nbButtonsBeforeCreate = await productoComponentsPage.countDeleteButtons();

    await productoComponentsPage.clickOnCreateButton();

    await promise.all([productoUpdatePage.setNombreInput('nombre'), productoUpdatePage.setPrecionInput('5')]);

    expect(await productoUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    expect(await productoUpdatePage.getPrecionInput()).to.eq('5', 'Expected precion value to be equals to 5');

    await productoUpdatePage.save();
    expect(await productoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Producto', async () => {
    const nbButtonsBeforeDelete = await productoComponentsPage.countDeleteButtons();
    await productoComponentsPage.clickOnLastDeleteButton();

    productoDeleteDialog = new ProductoDeleteDialog();
    expect(await productoDeleteDialog.getDialogTitle()).to.eq('soaintApp.producto.delete.question');
    await productoDeleteDialog.clickOnConfirmButton();

    expect(await productoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
