/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudentComponentsPage, StudentDeleteDialog, StudentUpdatePage } from './student.page-object';

const expect = chai.expect;

describe('Student e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let studentUpdatePage: StudentUpdatePage;
    let studentComponentsPage: StudentComponentsPage;
    let studentDeleteDialog: StudentDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Students', async () => {
        await navBarPage.goToEntity('student');
        studentComponentsPage = new StudentComponentsPage();
        await browser.wait(ec.visibilityOf(studentComponentsPage.title), 5000);
        expect(await studentComponentsPage.getTitle()).to.eq('slmanagerApp.student.home.title');
    });

    it('should load create Student page', async () => {
        await studentComponentsPage.clickOnCreateButton();
        studentUpdatePage = new StudentUpdatePage();
        expect(await studentUpdatePage.getPageTitle()).to.eq('slmanagerApp.student.home.createOrEditLabel');
        await studentUpdatePage.cancel();
    });

    it('should create and save Students', async () => {
        const nbButtonsBeforeCreate = await studentComponentsPage.countDeleteButtons();

        await studentComponentsPage.clickOnCreateButton();
        await promise.all([
            studentUpdatePage.setFirstNameInput('firstName'),
            studentUpdatePage.setLastNameInput('lastName')
            // studentUpdatePage.lectureSelectLastOption(),
        ]);
        expect(await studentUpdatePage.getFirstNameInput()).to.eq('firstName');
        expect(await studentUpdatePage.getLastNameInput()).to.eq('lastName');
        await studentUpdatePage.save();
        expect(await studentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await studentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Student', async () => {
        const nbButtonsBeforeDelete = await studentComponentsPage.countDeleteButtons();
        await studentComponentsPage.clickOnLastDeleteButton();

        studentDeleteDialog = new StudentDeleteDialog();
        expect(await studentDeleteDialog.getDialogTitle()).to.eq('slmanagerApp.student.delete.question');
        await studentDeleteDialog.clickOnConfirmButton();

        expect(await studentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
