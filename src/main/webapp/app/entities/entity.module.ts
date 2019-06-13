import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'student',
                loadChildren: './student/student.module#SlmanagerStudentModule'
            },
            {
                path: 'professor',
                loadChildren: './professor/professor.module#SlmanagerProfessorModule'
            },
            {
                path: 'lecture',
                loadChildren: './lecture/lecture.module#SlmanagerLectureModule'
            },
            {
                path: 'student',
                loadChildren: './student/student.module#SlmanagerStudentModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlmanagerEntityModule {}
