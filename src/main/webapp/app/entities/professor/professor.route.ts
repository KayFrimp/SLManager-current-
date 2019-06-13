import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Professor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';
import { ProfessorComponent } from './professor.component';
import { ProfessorDetailComponent } from './professor-detail.component';
import { ProfessorUpdateComponent } from './professor-update.component';
import { ProfessorDeletePopupComponent } from './professor-delete-dialog.component';
import { IProfessor } from 'app/shared/model/professor.model';

@Injectable({ providedIn: 'root' })
export class ProfessorResolve implements Resolve<IProfessor> {
    constructor(private service: ProfessorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfessor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Professor>) => response.ok),
                map((professor: HttpResponse<Professor>) => professor.body)
            );
        }
        return of(new Professor());
    }
}

export const professorRoute: Routes = [
    {
        path: '',
        component: ProfessorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slmanagerApp.professor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProfessorDetailComponent,
        resolve: {
            professor: ProfessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slmanagerApp.professor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProfessorUpdateComponent,
        resolve: {
            professor: ProfessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slmanagerApp.professor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProfessorUpdateComponent,
        resolve: {
            professor: ProfessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slmanagerApp.professor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const professorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProfessorDeletePopupComponent,
        resolve: {
            professor: ProfessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slmanagerApp.professor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
