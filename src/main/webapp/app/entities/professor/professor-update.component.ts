import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProfessor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';

@Component({
    selector: 'jhi-professor-update',
    templateUrl: './professor-update.component.html'
})
export class ProfessorUpdateComponent implements OnInit {
    professor: IProfessor;
    isSaving: boolean;

    constructor(protected professorService: ProfessorService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ professor }) => {
            this.professor = professor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.professor.id !== undefined) {
            this.subscribeToSaveResponse(this.professorService.update(this.professor));
        } else {
            this.subscribeToSaveResponse(this.professorService.create(this.professor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessor>>) {
        result.subscribe((res: HttpResponse<IProfessor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
