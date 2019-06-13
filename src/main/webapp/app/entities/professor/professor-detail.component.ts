import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessor } from 'app/shared/model/professor.model';

@Component({
    selector: 'jhi-professor-detail',
    templateUrl: './professor-detail.component.html'
})
export class ProfessorDetailComponent implements OnInit {
    professor: IProfessor;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ professor }) => {
            this.professor = professor;
        });
    }

    previousState() {
        window.history.back();
    }
}
