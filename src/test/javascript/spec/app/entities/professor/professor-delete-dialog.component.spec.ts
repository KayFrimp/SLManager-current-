/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SlmanagerTestModule } from '../../../test.module';
import { ProfessorDeleteDialogComponent } from 'app/entities/professor/professor-delete-dialog.component';
import { ProfessorService } from 'app/entities/professor/professor.service';

describe('Component Tests', () => {
    describe('Professor Management Delete Component', () => {
        let comp: ProfessorDeleteDialogComponent;
        let fixture: ComponentFixture<ProfessorDeleteDialogComponent>;
        let service: ProfessorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlmanagerTestModule],
                declarations: [ProfessorDeleteDialogComponent]
            })
                .overrideTemplate(ProfessorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProfessorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfessorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
