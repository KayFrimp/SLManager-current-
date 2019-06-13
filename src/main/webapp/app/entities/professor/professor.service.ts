import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfessor } from 'app/shared/model/professor.model';

type EntityResponseType = HttpResponse<IProfessor>;
type EntityArrayResponseType = HttpResponse<IProfessor[]>;

@Injectable({ providedIn: 'root' })
export class ProfessorService {
    public resourceUrl = SERVER_API_URL + 'api/professors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/professors';

    constructor(protected http: HttpClient) {}

    create(professor: IProfessor): Observable<EntityResponseType> {
        return this.http.post<IProfessor>(this.resourceUrl, professor, { observe: 'response' });
    }

    update(professor: IProfessor): Observable<EntityResponseType> {
        return this.http.put<IProfessor>(this.resourceUrl, professor, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IProfessor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfessor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfessor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
