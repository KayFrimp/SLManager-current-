import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SlmanagerSharedModule } from 'app/shared';
import {
    ProfessorComponent,
    ProfessorDetailComponent,
    ProfessorUpdateComponent,
    ProfessorDeletePopupComponent,
    ProfessorDeleteDialogComponent,
    professorRoute,
    professorPopupRoute
} from './';

const ENTITY_STATES = [...professorRoute, ...professorPopupRoute];

@NgModule({
    imports: [SlmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProfessorComponent,
        ProfessorDetailComponent,
        ProfessorUpdateComponent,
        ProfessorDeleteDialogComponent,
        ProfessorDeletePopupComponent
    ],
    entryComponents: [ProfessorComponent, ProfessorUpdateComponent, ProfessorDeleteDialogComponent, ProfessorDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlmanagerProfessorModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
