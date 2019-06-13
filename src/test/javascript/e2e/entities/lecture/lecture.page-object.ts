import { element, by, ElementFinder } from 'protractor';

export class LectureComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lecture div table .btn-danger'));
    title = element.all(by.css('jhi-lecture div h2#page-heading span')).first();

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

export class LectureUpdatePage {
    pageTitle = element(by.id('jhi-lecture-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    courseNameInput = element(by.id('field_courseName'));
    creditHoursInput = element(by.id('field_creditHours'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCourseNameInput(courseName) {
        await this.courseNameInput.sendKeys(courseName);
    }

    async getCourseNameInput() {
        return this.courseNameInput.getAttribute('value');
    }

    async setCreditHoursInput(creditHours) {
        await this.creditHoursInput.sendKeys(creditHours);
    }

    async getCreditHoursInput() {
        return this.creditHoursInput.getAttribute('value');
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

export class LectureDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lecture-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lecture'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
