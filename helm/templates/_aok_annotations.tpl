{{- define "aok.tagList" -}}
aok-systems.de/contact: {{ .contact }}
aok-systems.de/origin: https://argocd-server-argocd.apps.cx.ocp.aws.aok-systems.de/applications/{{default "If you can read this, there was a problem setting the origin" .origin }}
{{- end -}}
{{- define "aok.annotations" -}}
{{- with . -}}
annotations:
{{- include "aok.tagList" . | nindent 2 }}
{{- end -}}
{{- end -}}
{{- define "aok.dictAnnotations" -}}
{{- if . -}}
{{- toYaml . }}
{{- end -}}
{{- end -}}
{{- define "aok.podAnnotations" -}}
{{- if or .Values.podAnnotations .Values.aokMetadata -}}
annotations:
{{ include "aok.dictAnnotations" .Values.podAnnotations | indent 2 }}
{{- include "aok.tagList" .Values.aokMetadata | nindent 2 }}
{{- end -}}
{{- end -}}
{{- define "aok.serviceAccountAnnotations" -}}
{{- if or .Values.serviceAccount.annotations .Values.aokMetadata -}}
annotations:
{{ include "aok.dictAnnotations" .Values.serviceAccount.annotations | indent 2 }}
{{- include "aok.tagList" .Values.aokMetadata | nindent 2 }}
{{- end -}}
{{- end -}}
{{- define "aok.routeAnnotations" -}}
{{- if or .Values.route.annotations .Values.aokMetadata -}}
annotations:
{{ include "aok.dictAnnotations" .Values.route.annotations | indent 2 }}
{{- include "aok.tagList" .Values.aokMetadata | nindent 2 }}
{{- end -}}
{{- end -}}
{{- define "aok.ingressAnnotations" -}}
annotations:
{{ include "aok.dictAnnotations" .Values.ingress.annotations | indent 2 }}
{{- include "aok.tagList" .Values.aokMetadata | nindent 2 }}
{{- end -}}