/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LectureComponentsPage, LectureDeleteDialog, LectureUpdatePage } from './lecture.page-object';

const expect = chai.expect;

describe('Lecture e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let lectureUpdatePage: LectureUpdatePage;
    let lectureComponentsPage: LectureComponentsPage;
    let lectureDeleteDialog: LectureDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Lectures', async () => {
        await navBarPage.goToEntity('lecture');
        lectureComponentsPage = new LectureComponentsPage();
        await browser.wait(ec.visibilityOf(lectureComponentsPage.title), 5000);
        expect(await lectureComponentsPage.getTitle()).to.eq('slmanagerApp.lecture.home.title');
    });

    it('should load create Lecture page', async () => {
        await lectureComponentsPage.clickOnCreateButton();
        lectureUpdatePage = new LectureUpdatePage();
        expect(await lectureUpdatePage.getPageTitle()).to.eq('slmanagerApp.lecture.home.createOrEditLabel');
        await lectureUpdatePage.cancel();
    });

    it('should create and save Lectures', async () => {
        const nbButtonsBeforeCreate = await lectureComponentsPage.countDeleteButtons();

        await lectureComponentsPage.clickOnCreateButton();
        await promise.all([lectureUpdatePage.setCourseNameInput('courseName'), lectureUpdatePage.setCreditHoursInput('5')]);
        expect(await lectureUpdatePage.getCourseNameInput()).to.eq('courseName');
        expect(await lectureUpdatePage.getCreditHoursInput()).to.eq('5');
        await lectureUpdatePage.save();
        expect(await lectureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await lectureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Lecture', async () => {
        const nbButtonsBeforeDelete = await lectureComponentsPage.countDeleteButtons();
        await lectureComponentsPage.clickOnLastDeleteButton();

        lectureDeleteDialog = new LectureDeleteDialog();
        expect(await lectureDeleteDialog.getDialogTitle()).to.eq('slmanagerApp.lecture.delete.question');
        await lectureDeleteDialog.clickOnConfirmButton();

        expect(await lectureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
