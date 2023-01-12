# Gson vs. Kotlinx.Serialization benchmark
Project comparing the performance of Gson and Kotlinx.Serialization libraries  
Deserialization time benchmarks located in benchmark module, models and everything needed are in benchmarkable module  
You can find benchmarks, measuring deserialization for:
1) simple data classes consisting of primitive types, with and without nesting
2) small/large lists of simple classes
3) small/large lists of polymorphic structures
4) data classes consisting of classes, which are not serializable by default
