import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';

@Component({
    selector: 'jhi-professor-delete-dialog',
    templateUrl: './professor-delete-dialog.component.html'
})
export class ProfessorDeleteDialogComponent {
    professor: IProfessor;

    constructor(
        protected professorService: ProfessorService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.professorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'professorListModification',
                content: 'Deleted an professor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-professor-delete-popup',
    template: ''
})
export class ProfessorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ professor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProfessorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.professor = professor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/professor', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/professor', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
