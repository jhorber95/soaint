import { element, by, ElementFinder } from 'protractor';

export class DetalleVentaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-detalle-venta div table .btn-danger'));
  title = element.all(by.css('jhi-detalle-venta div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DetalleVentaUpdatePage {
  pageTitle = element(by.id('jhi-detalle-venta-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  ventaSelect = element(by.id('field_venta'));
  pruductoSelect = element(by.id('field_pruducto'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async ventaSelectLastOption(): Promise<void> {
    await this.ventaSelect.all(by.tagName('option')).last().click();
  }

  async ventaSelectOption(option: string): Promise<void> {
    await this.ventaSelect.sendKeys(option);
  }

  getVentaSelect(): ElementFinder {
    return this.ventaSelect;
  }

  async getVentaSelectedOption(): Promise<string> {
    return await this.ventaSelect.element(by.css('option:checked')).getText();
  }

  async pruductoSelectLastOption(): Promise<void> {
    await this.pruductoSelect.all(by.tagName('option')).last().click();
  }

  async pruductoSelectOption(option: string): Promise<void> {
    await this.pruductoSelect.sendKeys(option);
  }

  getPruductoSelect(): ElementFinder {
    return this.pruductoSelect;
  }

  async getPruductoSelectedOption(): Promise<string> {
    return await this.pruductoSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DetalleVentaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-detalleVenta-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-detalleVenta'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
