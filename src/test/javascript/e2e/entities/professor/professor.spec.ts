/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfessorComponentsPage, ProfessorDeleteDialog, ProfessorUpdatePage } from './professor.page-object';

const expect = chai.expect;

describe('Professor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let professorUpdatePage: ProfessorUpdatePage;
    let professorComponentsPage: ProfessorComponentsPage;
    let professorDeleteDialog: ProfessorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Professors', async () => {
        await navBarPage.goToEntity('professor');
        professorComponentsPage = new ProfessorComponentsPage();
        await browser.wait(ec.visibilityOf(professorComponentsPage.title), 5000);
        expect(await professorComponentsPage.getTitle()).to.eq('slmanagerApp.professor.home.title');
    });

    it('should load create Professor page', async () => {
        await professorComponentsPage.clickOnCreateButton();
        professorUpdatePage = new ProfessorUpdatePage();
        expect(await professorUpdatePage.getPageTitle()).to.eq('slmanagerApp.professor.home.createOrEditLabel');
        await professorUpdatePage.cancel();
    });

    it('should create and save Professors', async () => {
        const nbButtonsBeforeCreate = await professorComponentsPage.countDeleteButtons();

        await professorComponentsPage.clickOnCreateButton();
        await promise.all([professorUpdatePage.setFirstNameInput('firstName'), professorUpdatePage.setLastNameInput('lastName')]);
        expect(await professorUpdatePage.getFirstNameInput()).to.eq('firstName');
        expect(await professorUpdatePage.getLastNameInput()).to.eq('lastName');
        await professorUpdatePage.save();
        expect(await professorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await professorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Professor', async () => {
        const nbButtonsBeforeDelete = await professorComponentsPage.countDeleteButtons();
        await professorComponentsPage.clickOnLastDeleteButton();

        professorDeleteDialog = new ProfessorDeleteDialog();
        expect(await professorDeleteDialog.getDialogTitle()).to.eq('slmanagerApp.professor.delete.question');
        await professorDeleteDialog.clickOnConfirmButton();

        expect(await professorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
