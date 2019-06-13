import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILecture } from 'app/shared/model/lecture.model';
import { LectureService } from './lecture.service';

@Component({
    selector: 'jhi-lecture-update',
    templateUrl: './lecture-update.component.html'
})
export class LectureUpdateComponent implements OnInit {
    lecture: ILecture;
    isSaving: boolean;

    constructor(protected lectureService: LectureService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lecture }) => {
            this.lecture = lecture;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lecture.id !== undefined) {
            this.subscribeToSaveResponse(this.lectureService.update(this.lecture));
        } else {
            this.subscribeToSaveResponse(this.lectureService.create(this.lecture));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILecture>>) {
        result.subscribe((res: HttpResponse<ILecture>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
