import { element, by, ElementFinder } from 'protractor';

export class StudentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-student div table .btn-danger'));
    title = element.all(by.css('jhi-student div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StudentUpdatePage {
    pageTitle = element(by.id('jhi-student-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    firstNameInput = element(by.id('field_firstName'));
    lastNameInput = element(by.id('field_lastName'));
    enrolledOnSelect = element(by.id('field_enrolledOn'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setFirstNameInput(firstName) {
        await this.firstNameInput.sendKeys(firstName);
    }

    async getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    async setLastNameInput(lastName) {
        await this.lastNameInput.sendKeys(lastName);
    }

    async getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    async enrolledOnSelectLastOption() {
        await this.enrolledOnSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async enrolledOnSelectOption(option) {
        await this.enrolledOnSelect.sendKeys(option);
    }

    getEnrolledOnSelect(): ElementFinder {
        return this.enrolledOnSelect;
    }

    async getEnrolledOnSelectedOption() {
        return this.enrolledOnSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class StudentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-student-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-student'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
